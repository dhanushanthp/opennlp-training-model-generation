import re

_train_data_source = "test.train";
_train_data_output = "output.train";

unique_names = set();

def write_line(text,file_name):
    with open(file_name, "a") as file:
        file.write(text);

def read_line(file_name):
    f = open(file_name, "r")
    lines = f.readlines()
    f.close();
    return lines;

def mainCreateNameList():
    sentences = read_line(_train_data_source);
    for sentence in sentences:
        namesStr = sentence.split('<END>')
        for nameStr in namesStr:
            if "<START:person>" in nameStr :
                name = re.search('<START:person>(.*)', nameStr)
                unique_names.add(name.group(1).strip());
    addNames(unique_names);

def mainCountUnique():
    sentences = read_line(_train_data_source);
    for sentence in sentences:
        namesStr = sentence.split('<END>')
        for nameStr in namesStr:
            if "<START:person>" in nameStr :
                name = re.search('<START:person>(.*)', nameStr)
                unique_names.add(name.group(1).strip());
    print ('#############################################')
    print('Total Number of Unique Names : ', len(unique_names))
    print ('#############################################')
    print('Total Number of Sentence : ', len(sentences))
    print ('#############################################')

def main():
    option_nom = input("Enter the option : \n")
    option_num = int(option_nom)
    if option_num > 2 :
        print("Option not available \n")
        main();
    else:
        if option_num == 1:
            mainCountUnique();
        elif option_num == 2:
            mainCreateNameList();
            mainCountUnique();
        print('Completed...');
    
def addNames(names):
    for name in names:
        write_line(name + '\n', _train_data_output);
    
if __name__ == '__main__':
    print ('Press 1 :  Check the total number of Unique names.');
    print ('Press 2 :  Create the Unique names list in file : ' + _train_data_output);
    main();            
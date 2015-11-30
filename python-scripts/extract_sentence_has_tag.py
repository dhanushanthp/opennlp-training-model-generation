import re

_test_data_source = "en-ner-person.train";
_train_data_output = "output.train";

def write_line(text,file_name):
    with open(file_name, "a") as file:
        file.write(text);

def read_line(file_name):
    f = open(file_name, "r")
    lines = f.readlines()
    f.close();
    return lines;

def main():
    for sentence in read_line(_test_data_source):
        if "<START:person>" in sentence:
            write_line(sentence, _train_data_output);
    
if __name__ == '__main__':
    main()


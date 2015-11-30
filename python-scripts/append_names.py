"""
Created to add the exist names with sentences patterns which has 
extract from total Wikipedia content.
"""
import re

_train_data_input = "test.train";
_names_data_source = "name.train";
_train_data_output = "output.train";

class variable:
    value = 0;

count = variable()

def write_line(text,file_name):
    with open(file_name, "a") as file:
        file.write(text);

def read_line(file_name):
    f = open(file_name, "r")
    lines = f.readlines()
    f.close();
    return lines;

names = read_line(_names_data_source);

def appen_name(sentence):
    appen_text = '';
    array_split = sentence.split("<START:person>");
    for word in array_split:
        if "<END>" in word :
            word_split = word.split("<END>");
            appen_text += "<START:person> " + names[count.value].rstrip() + " <END> " + word_split[1];
            count.value = count.value + 1;
        else:
            appen_text += word;
    return appen_text;

def process_content():
    for i in range(0,10):
        sentences = read_line(_train_data_input)
        for sentence in sentences:
            if "<START:person>" in sentence:
               write_line(appen_name(sentence), _train_data_output)
            else:
                write_line(sentence, _train_data_output)

def main():
    process_content();

if __name__ == '__main__':
    main();
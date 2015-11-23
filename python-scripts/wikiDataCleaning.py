_train_data_source = "/opt/data-extractor/data/open-nlp-train/en-ner-person.train-default";
_train_data_output = "/opt/data-extractor/data/open-nlp-train/en-ner-person.train";

def write_line(text,file_name):
    with open(file_name, "a") as file:
        file.write(text);
    
def read_line(file_name):
    f = open(file_name, "r")
    lines = f.readlines()
    f.close();
    return lines;

def process_content():
    lines = read_line(_train_data_source);
    for x in lines:
        if (("between <START:person>" not in x) 
            and ("0 <START" not in x)
            and ("1 <START" not in x)
            and ("2 <START" not in x)
            and ("3 <START" not in x)
            and ("4 <START" not in x)
            and ("5 <START" not in x)
            and ("6 <START" not in x)
            and ("7 <START" not in x)
            and ("8 <START" not in x)
            and ("9 <START" not in x)
            and ("<END> 0" not in x)
            and ("<END> 1" not in x)
            and ("<END> 2" not in x)
            and ("<END> 3" not in x)
            and ("<END> 4" not in x)
            and ("<END> 5" not in x)
            and ("<END> 6" not in x)
            and ("<END> 7" not in x)
            and ("<END> 8" not in x)
            and ("<END> 9" not in x)
            and (", <START:person>" not in x) 
            and (" than <START:person>" not in x) 
            and (" Than <START:person>" not in x) 
            and ("( <START:person>" not in x) 
            and (" <END> first" not in x) 
            and (" with <START:person>" not in x)
            and (" following <START:person>" not in x)
            and (" With <START:person>" not in x)
            and (" Following <START:person>" not in x) 
            and (" and <START:person>" not in x) 
            and (" <END> is " not in x) 
            and (" <END> the " not in x) 
            and (" . <START:per" not in x)
            and (" <END> did " not in x)
            and (" by <START:" not in x)
            and ("<END> and " not in x) 
            and ("<START:" not in x[0:7]) 
            and (" <END> (" not in x)): 
                write_line(x, _train_data_output);
                print(x);

def process_content_individual():
    lines = read_line(_train_data_source);
    for x in lines:
        if ((" from <START" in x) 
            or ("of <START" in x)): 
                write_line(x, _train_data_output);
                print(x);

# process_content();
process_content_individual();
'''
The application has been developed to change the default training data
to our own requirement  by replacing some POS tags to build a training
model.
'''
f_in = open("train.txt", "r")
in_lines = f_in.readlines()
out = []

count = 0
while (count < len(in_lines)):
    if in_lines[count].strip() != '':
        list_values = in_lines[count].split()  # split to get the data pattern as <word> <pos> <train_tag>
        try:
            list_values_next = in_lines[count + 1].split()  # to remove in <IN> <B-PP>
        except Exception as e:
            print (e)
        else:
            pass
        finally:
            pass
        print (count)
        if list_values[2] == 'B-PP':
            list_values[2] = 'O'
            out.append(list_values)
            count = count + 1
        elif list_values[1] == 'DT' and list_values[2] == 'B-NP' :  # to remove the <DT> <B-NP>
            list_values[2] = 'O'
            out.append(list_values)
            if list_values_next[2] == 'I-NP' :  # if next is <NN> <I-NP> then that need to be changed as <B-NP>. Because B is consider as a starting point of the chunk
                list_values_next[2] = 'B-NP'
                out.append(list_values_next)
                count = count + 1;
                print ('in ' , count)
            count = count + 1
        else :
            out.append(list_values)
            count = count + 1
    else:
        out.append("")
        count = count + 1
f_in.close() 

f_out = open("en-chunker.train", "w")  # opens a new file in the writing mode
for cur_list in out:
    for count in cur_list:
        f_out.write(str(count) + " ")  # writes each number, plus a tab
    f_out.write("\n")  # writes a newline
f_out.close() 

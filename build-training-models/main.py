f_in = open("train.txt", "r")  # opens a file in the reading mode
in_lines = f_in.readlines()  # reads it line by line
out = []

i = 0
while (i < len(in_lines)):
# for i in range(len(in_lines)):
	# print (i)
	if in_lines[i].strip() != '':
		list_values = in_lines[i].split()  # to remove in IN B-PP
		try:
			list_values_next = in_lines[i + 1].split()  # to remove in IN B-PP
		except Exception as e:
			print (e)
		else:
			pass
		finally:
			pass
	    # print (in_lines[i])
		print (i)
		if list_values[2] == 'B-PP':
			list_values[2] = 'O'
			out.append(list_values)
			i = i + 1
		elif list_values[1] == 'DT' and list_values[2] == 'B-NP' :  # to remove the DT B-NP
			list_values[2] = 'O'
			out.append(list_values)
			if list_values_next[2] == 'I-NP' :  # if next is NN I-NP
				list_values_next[2] = 'B-NP'
				out.append(list_values_next)
				i = i + 1;
				print ('in ' , i)
			i = i + 1
		else :
			out.append(list_values)
			i = i + 1
	else:
		out.append("")
		i = i + 1
f_in.close() 

f_out = open("en-chunker.train", "w")  # opens a new file in the writing mode
for cur_list in out:
	for i in cur_list:
		f_out.write(str(i) + " ")  # writes each number, plus a tab
	f_out.write("\n")  # writes a newline
f_out.close() 

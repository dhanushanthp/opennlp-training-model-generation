### Need to add the opennlp to system path
```
nano ~/.bash_profile
export OPENNLP_HOME=/home/dhanu/Downloads/opennlp
export PATH=$PATH:$OPENNLP_HOME/bin
source ~/.bash_profile
```
### Now we can run the opennlp in command line.
	
### All the text need to be in data folder. And that need to be with .txt extension. 

### Once the text file added to data folder. 
```
cd ner_perl
bin/dhanu_code.eng
```
### Now the eng.train file will be created in ner_perl directory.

### The eng.train need to be run with opennlp to create training data for opennlp.
```
opennlp TokenNameFinderConverter conll03 -lang en -types per -data eng.train > corpus_train.txt
```
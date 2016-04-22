# DataShare

DataShare allows for valuable knowledge about people and companies 
locked within hundreds of pages of documents inside a computer 
to be sieved into indexes and shared securely within a network of 
trusted individuals, fostering unforeseen collaboration and prompting 
new and better investigations that uncover corruption, transnational 
crime and abuse of power.

[DataShare: connecting local data with a global collective intelligence](https://www.newschallenge.org/challenge/data/refinement/datashare-connecting-local-data-with-a-global-collective-intelligence)

DatasShare is currently based on the following projects:

 - [Apache Tika](https://tika.apache.org/) v1.12
 (Apache licence)
 
 - [Stanford CoreNLP](http://stanfordnlp.github.io/CoreNLP) v3.6.0
 (Composite GPL v3+ licence)

 - [Apache OpenNLP](https://opennlp.apache.org/) v1.6.0
 (Apache licence)
 
 
## Overview of Current Release 

Currently, this project mainly allows for **named entities extraction**.

More precisely, it consists in running `NLPPipeline`s (`CoreNLPPipeline`, `OpenNLPPipeline`)

- **Input**: `String` or document `Path` of which mime-type is supported by (Tika) `DocumentParser`

- **Output**: sequence of annotations `(`token, `[`part-of-speech, `]` named entity category`)`


Documents are parsed by Tika.

Content language is detected by Tika.

Supported named entity categories: **Person**, **Organization**, **Location**.

Supported languages: **English**, **Spanish**, **French** and **German**.

- OpenNLP 
   - NER: English, Spanish and French  
   - POS: English, Spanish, French and German

- CoreNLP 
  - NER: English, Spanish and German
  - POS: English, Spanish, French and German


## Usage Example

`Main.java` exhibits basic usage.


## Compilation

Requires [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and [Maven](http://maven.apache.org/download.cgi)


## Todo

 - Integrate a `GATENLPPipeline`

 - Add command-line interface

 - Process all documents from folder / corpus

 - Asynchronous execution & multi-threading
 
 - Make external NLP projects optional
 

## License

DataShare is released under the [GNU General Public License](http://www.gnu.org/licenses/gpl.html)
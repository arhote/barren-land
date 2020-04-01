Barren Land Analysis
=====================
## Building the application
Use the following commands to download and build the application:
```
cd <Path where you want to download the app>
git clone https://github.com/arhote/barren-land.git
cd barren-land/src
javac @sources.txt
```
**Please Note: You'll need to update your path to match Windows slashes if you're running Windows**

## Running the application
The following command will run the program in the terminal and prompt for user input:
```
java Main
```
If you want to test the program with a file rather than manually entering the barren land squares then create a file of 
the following format in the same directory you run the above command.
```
xmin1 ymin1 xmax1 ymax1
xmin2 ymin2 xmax2 ymax2
xmin3 ymin3 xmax3 ymax3
...
xminN yminN xmaxN ymaxN
-1
```
After you've created the file instead run the following command:
```
java Main < inputFileName
```
The result can be sent to an output file by appending `> outputFileName` to the above command.
**Note:** The results file will still contain the prompts to input barren land segments, these lines can be ignored.
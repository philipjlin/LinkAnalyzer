# Distributed Link Analyzer


## Repository
<https://github.com/philipjlin/LinkAnalyzer>


## Description
This program is an implementation of a distributed link analyzer that stores the information of input files from different remote nodes containing URLs, timestamps, and tags, and can return results of queries to the database system from any of those remote systems.


## Input Files
* files.1 - input files for node 1
* files.2 - input files for node 2


## Class Overview
Domain Objects <br>
    - Link - Defines a link, which consists of a URL, timestamp, and a list of tags that are parsed from a String taken from the an input file. <br>

    - LinkAnalyzerImpl - Implements an object that can return results of queries for links (by timestamp, URL, or tags) from all nodes registered to the link analyzer. A method for registering nodes with the link analyzer is included, as is a main method that registers the link analyzer with the RMI registry with a specified URL. <br>

    - LinkAnalyzerNodeImpl - Implements an object that can return results of queries for links from the set of input links associated with the node. Contains a main method that specifies the input files used and registers the node with the link analyzer. <br>


## Interface Overview
LinkAnalyzer - Interface implemented by a LinkAnalyzerImpl object, with method headers to return links by timestamp, URL, or tags. Contains method to register nodes for use by the link analyzer. This interface extends Remote so it's methods can be invoked from a non-local virtual machine. <br>

LinkAnalyzerNode - Interface implemented by a LinkAnalyzerNodeImpl object, with method headers to return links by timestamp, URL, or tags. A node is used to keep track of a specific set of links on a system. This interface extends Remote so it's methods can be invoked from a non-local virtual machine. <br>


## Tests
TestFile - Runs tests to set up the link analyzer and nodes to register with the link analyzer and then parse the input files associated with each node.


## Run Instructions
From current directory where project is: <br>

rmiregistry -J-Dcp=“.:/linkanalyzer” <br>
javac linkanalyzer/*.java <br>

java -ea -cp . linkanalyzer.LinkAnalyzerImpl <br>
java -ea -cp . linkanalyzer.LinkAnalyzerNodeImpl ./files.1 <br>
java -ea -cp . linkanalyzer.LinkAnalyzerNodeImpl ./files.2 <br>

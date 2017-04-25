# Json Mock Data Generator

A simple tool for generating JSON mock data based on the configurable structure.

There is a lot of online tool for the similar usage like [JSON Generator](http://www.json-generator.com), but all of them have a little problem - they fail when generating a big amount of data (milions of elements). For such purposes a local processing must be used.

## Features

The tool provides a simple configurable definition of the generated result:

- user-defined structure of a document (sub-elements),
- user-defined repeat of element,
- pre-defined value generators like name, e-mail, phone, etc.
- including a content of a local file,
- random values (numbers, dates, strings) with minimums and maximuns or
- random selection from a user-defined set.

## Configuration

To be done.

## Running

First, clone or fork the repo:

```
$ git clone git@github.com:ttulka/json-mock-data-generator.git
$ cd json-mock-data-generator
```
Now build it:
```
$ mvn clean package
```
Once this completes, a jar file will have been generated in the target folder.

And finally run it: 

```
$ java -jar target/json-mock-data-generator-<version>.jar
```

For the test purposes example configurations could be used:

```
> Configuration file: examples/simple.json
```
or
```
> Configuration file: examples/full.json
```  

## License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
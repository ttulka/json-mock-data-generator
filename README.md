# JSON Mock Data Generator

A simple tool for generating JSON mock data based on the configurable structure.

There are a lot of online tools for similar usage like [JSON Generator](http://www.json-generator.com) but all of them have a little problem - they fail when generating a big amount of data (milions of elements). For this purpose a local processing must be used.

## Features

The tool provides a simple configurable definition of the generated result:

- user-defined structure of a document (sub-elements),
- user-defined repeat of element,
- pre-defined value generators like name, e-mail, phone, etc.
- including a content of a local file,
- random values (numbers, dates, strings) with minimums and maximuns or
- random selection from a user-defined set.

## Configuration

Example configuration files could be found in the `/examples` folder.
The sample outputs from those configurations are in the same folder with a suffix `.out`.

Configuration is a [JSON](http://www.json.org) file with the following formal definition:

- The root element `items` is an array,
- each non-root element must define an attribute `name`,
- any element can define children elements as an array element `items`,
- if no `repeat` attribute is defined there is only one *composite* child element,
- a composite element can define an attribute `repeat` - "min[,max]" (default 1),
- if a composite element define an attribute `repeat` children elements are in an array,
- an element can define a `value` or `values` (comma-separated values) attribute,
- an array element has the type `array` and must define `values`,
- if an element doesn't define a value(s), must define a `type` attribute,
- types of elements are following:
	- `integer`, a random int number, can be limited with `min` and `max` attributes,
	- `float`, a random float number, can be limited with `min` and `max` attributes,
	- `date`, a random date, can be limited with `min` and `max` attributes,
	- `lorem`, a random "Lorem Ipsum" text, can be limited with `min` and `max` attributes,
	- `id`, a random ID string, 
	- `fullName`, a random full name,
	- `firstName`, a random first name,
	- `lastName`, a random last name,
	- `title`, a random title,
	- `email`, a random e-mail address,
	- `phone`, a random phone number,
	- `address`, a random address (street, zip and city),
	- `file`, loads the content from a local file, must define a `path` attribute as a path to a file related to the execution folder.
	
A very simple configuration file could look like this:

```
{
	items: [
		{
			name: "header",
			value: "Example configuration file for a medicinal data."
		},
		{
			name: "patients",
			repeat: "100",
			items: [
				{
					name: "personalId",
					type: "id"
				},
				{
					name: "name",
					type: "fullName"
				},
				{
					name: "gender",
					values: "male, female"
				},
				{
					name: "age",
					type: "integer",
					min: "20",
					max: "100"
				},
				{
					name: "contact",
					type: "phone"
				},
				{
					name: "home",
					type: "address"
				},
				{
					name: "doctor",
					items: [
						{
							name: "title",
							type: "title"
						},
						{
							name: "name",
							type: "fullName"
						},
                        {
                            name: "departments",
                            type: "array",
                            repeat: "1,3",
                            values: "ICU, EU, ED, CCU, HF"
                        }
					]
				}
			]
		},
		{
			name: "updated",
			type: "date",
			min: "1990-01-01"
		},
		{
			name: "footer",
			type: "file",
			path: "footer.txt"
		}
	]
}
```
	
## Running

First, clone or fork the repository:

```
$ git clone git@github.com:ttulka/json-mock-data-generator.git
$ cd json-mock-data-generator
```
Now build it:
```
$ mvn clean package
```
Once this is completed a jar file has been generated in the target folder.

And finally run it: 

```
$ java -jar target/json-mock-data-generator-<version>.jar
```

For test purposes example configurations could be used:

```
> Configuration file: examples/simple.json
```
or
```
> Configuration file: examples/full.json
```  

### Generating a set of files

To generate more than only one file based on the same configuration it is possible to define how many file creates the generator:

```
$ java -jar target/json-mock-data-generator-<version>.jar --files 100
```

Now ten files will be generated.

## Guess Configuration from a JSON document

To save the effort with defining a configuration files for complex structures there is a possibility to let the program guess the configuration from an example JSON document.
The generated configuration file could be then manually adapt especially in the meaning of the element types.

``
$ java -jar target/json-mock-data-generator-<version>.jar --guess <path-to-json-file>
``

The configuration guess will be generated into `<path-to-json-file>.guess-conf` file.

## License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)

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

Example configuration files could be found in the `/examples` folder.
The sample outputs from those configurations are in the same folder with a suffix `.out`.

Configuration is a [JSON](http://www.json.org) file with the following formal definition:

- The root element `items` is an array,
- each non-root element must define an attribute `name`,
- any element can define children elements as an array element `items`,
- an element with children elements is called a *composite element* ,
- a composite element can define an attribute `repeat` - "min[,max]" (default 1),
- a composite element can define no other attributes,
- an element can define a `value` or `values` (comma-separated values) attribute,
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

```json
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
                    name: "doctors",
                    repeat: "1,5",
                    items: [
                        {
                            name: "title",
                            type: "title"
                        },
                        {
                            name: "name",
                            type: "fullName"
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
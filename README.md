# backend - code
This is a Spring Based Maven application.
Which exposes rest end points

1. PUT /expense 
	- Request Body: {
		"date" : "08/11/2017",
		"value" : 36.93,
		"reason" : "this is my expense reason"
	}

2. GET /expenses

	- Response Body: [{
        "date": "07/10/2017",
        "value": 12.43,
        "vat": 2.07,
        "reason": "this is my first expense"
    },
    {
        "date": "08/10/2017",
        "value": 42.43,
        "vat": 7.07,
        "reason": "this is my second expense"
    },
    {
        "date": "08/10/2017",
        "value": 36.93,
        "vat": 6.16,
        "reason": "this is my third expense"
    },
    {
        "date": "08/11/2017",
        "value": 36.93,
        "vat": 6.16,
        "reason": "this is my fourth expense"
    }
]

## Building application
1. Application can be build using maven as mvn clean install, copy the war file in tomcat webapp folder.
2. Other way to, import the project in eclipse and build and deploy integrated tomcat in eclipse/idea IntelliJ

## Viewing webpage
1. User can navigate to http://<machine-ip-or-name>:<port>/engage/expense.html to enter expense
	- http://localhost:8080/engage/expense.html
2. User can navigate to http://<machine-ip-or-name>:<port>/engage/view.html to view entered expense
	- http://localhost:8080/engage/view.html

# engage
This is a Spring Based Maven application.
Which exposes rest end points

1. PUT /expense 

Request Body: 
{
	"date" : "08/11/2017",
	"value" : 36.93,
	"reason" : "this is my expense reason"
}

2. GET /expenses
Response Body:
[
    {
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

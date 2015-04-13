import json
import uuid

with open ('recommendation.json', 'r') as f:
	json_str = f.readlines()

build_object = json.loads(json_str[0])

work_places_list = []

x = 0
while x < build_object['plcs_wrk_count']:
    work_places_list.append("Work_{}".format(x))
    x = x+1

with open ("work_table.sql", "wa") as query:
    query.write("DROP TABLE WorkTable;\n")
    query.write("\n")
    query.write("CREATE TABLE WorkTable (Work_ID VARCHAR(200), Work_Name VARCHAR(200), PRIMARY KEY (Work_ID));\n")
    query.write("\n")
    i = 0
    for work_id in work_places_list:
        #Do only when it is the first in the row
        if i%10 == 0:
            query.write("INSERT INTO WorkTable (Work_ID, Work_Name) VALUES ")

        #DO THIS NO MATTER WHAT
        query.write("('{}', 'INSERT NAME HERE')".format(work_id))

        #Do only if it is the last in the row
        if i%10 == 9:
            query.write(";\n")
        else:
            query.write(",")
        i = i+1

interest_list = []

x = 0
while x < build_object['interest_count']:
	interest_list.append("interest_{}".format(x))
	x = x + 1

with open ("interest_table.sql", "wa") as query:
    query.write("DROP TABLE InterestTable;\n")
    query.write("\n")
    query.write("CREATE TABLE InterestTable (Interest_ID VARCHAR(200), Interest_Name VARCHAR(200), PRIMARY KEY (Interest_ID));\n")
    query.write("\n")
    i = 0
    for interest_id in interest_list:
        #Do only when it is the first in the row
        if i%10 == 0:
            query.write("INSERT INTO InterestTable (Interest_ID, Interest_Name) VALUES ")

        #DO THIS NO MATTER WHAT
        query.write("('{}', 'Interest Name')".format(interest_id))

        #Do only if it is the last in the row
        if i%10 == 9:
            query.write(";\n")
        else:
            query.write(",")
        i = i+1


relationship_table_list = []
person_table_list = []
employee_table_list = []

people = build_object['people']

for person in people:
	person_id = 'Person_{}'.format(person['id'])
	person_table_list.append(person_id)
	for interest in person['interests']:
		relationship_table_list.append({'Person_ID': person_id, 'Interest_ID': 'Interest_{}'.format(interest), 'Relationship_Number': '{}'.format(uuid.uuid4().hex)})
	for work in person['plcs_wrk']:
		employee_table_list.append({'Person_ID': person_id, 'Work_ID': 'Work_{}'.format(work), 'Employee_Number': '{}'.format(uuid.uuid4().hex)})


with open ("person_table.sql", "wa") as query:
    query.write("DROP TABLE PersonTable;\n")
    query.write("\n")
    query.write("CREATE TABLE PersonTable (Person_ID VARCHAR(200), Person_Name VARCHAR(200), PRIMARY KEY (Person_ID));\n")
    query.write("\n")
    i = 0
    for person in person_table_list:
        #Do only when it is the first in the row
        if i%10 == 0:
            query.write("INSERT INTO PersonTable (Person_ID, Person_Name) VALUES ")

        #DO THIS NO MATTER WHAT
        query.write("('{}', 'Name')".format(person))

        #Do only if it is the last in the row
        if i%10 == 9:
            query.write(";\n")
        else:
            query.write(",")
        i = i+1

with open ("employee_table.sql", "wa") as query:
    query.write("DROP TABLE EmployeeTable;\n")
    query.write("\n")
    query.write("CREATE TABLE EmployeeTable (Person_ID VARCHAR(200), Work_ID VARCHAR(200), Employee_Number VARCHAR(200), PRIMARY KEY (Employee_Number));\n")
    query.write("\n")
    i = 0
    for employee_dict in employee_table_list:
        #Do only when it is the first in the row
        if i%10 == 0:
            query.write("INSERT INTO EmployeeTable (Person_ID, Work_ID, Employee_Number) VALUES ")

        #DO THIS NO MATTER WHAT
        query.write("('{}', '{}', '{}')".format(employee_dict['Person_ID'], employee_dict['Work_ID'], employee_dict['Employee_Number']))

        #Do only if it is the last in the row
        if i%10 == 9:
            query.write(";\n")
        else:
            query.write(",")
        i = i+1

with open ("relationship_table.sql", "wa") as query:
    query.write("DROP TABLE RelationshipTable;\n")
    query.write("\n")
    query.write("CREATE TABLE RelationshipTable (Person_ID VARCHAR(200), Interest_ID VARCHAR(200), Relationship_Number VARCHAR(200), PRIMARY KEY (Relationship_Number));\n")
    query.write("\n")
    i = 0
    for relationship_dict in relationship_table_list:
        #Do only when it is the first in the row
        if i%10 == 0:
            query.write("INSERT INTO RelationshipTable (Person_ID, Interest_ID, Relationship_Number) VALUES ")

        #DO THIS NO MATTER WHAT
        query.write("('{}', '{}', '{}')".format(relationship_dict['Person_ID'], relationship_dict['Interest_ID'], relationship_dict['Relationship_Number']))

        #Do only if it is the last in the row
        if i%10 == 9:
            query.write(";\n")
        else:
            query.write(",")
        i = i+1


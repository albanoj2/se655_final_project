import redis
import json
import ast

def start():
    print 'start'
    r = redis.StrictRedis(host = 'localhost', port = 6379, db = 0)

    with open ('recommendation.json', 'r') as f:
        json_str = f.readlines()

    build_object = json.loads(json_str[0])

    relationship_table_list = []
    employee_table_list = []

    people = build_object['people']

    for person in people:
        person_id = 'Person_{}'.format(person['id'])
        for interest in person['interests']:
            relationship_table_list.append({'Person_ID': person_id, 'Interest_ID': 'Interest_{}'.format(interest)})
        for work in person['plcs_wrk']:
            employee_table_list.append({'Person_ID': person_id, 'Work_ID': 'Work_{}'.format(work)})


    for relationship in relationship_table_list:
        set_add(r, 'person.{}.interest'.format(relationship['Person_ID']), relationship['Interest_ID'])

    for employee in employee_table_list:
        set_add(r, 'person.{}.work'.format(employee['Person_ID']), employee['Work_ID'])
        set_add(r, 'work.{}'.format(employee['Work_ID']), employee['Person_ID'])

def set_add(r, key, value):
    current_val = r.get(key)
    if current_val == None:
        current_val = []

    else:
        current_val = ast.literal_eval(current_val)
    current_val.append(value)

    r.set(key, current_val)

if __name__ == "__main__":
    print 'started?'
    start()
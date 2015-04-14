import redis
import ast
from datetime import datetime
import csv

def start():

    r = redis.StrictRedis(host = 'localhost', port = 6379, db = 0)

    people = []
    i = 0
    while i < 1000:
        people.append('Person_{}'.format(i))
        i = i + 1

    #print people

    query1(r, people)
    query2(r, people)
    query3(r, people)

#Get coworkers along with shared workplace
def query1(r, people_list):
    query_1_execution_times = []

    work_place_return = dict()
    
    i = 0
    while i < 30:

        start = datetime.now()

        for person in people_list:
            return_dict = dict()
            workplace_array = ast.literal_eval(r.get('person.{}.work'.format(person)))

            for work in workplace_array:
                coworkers = ast.literal_eval(r.get('work.{}'.format(work)))
                return_dict[work] = coworkers

            work_place_return[person] = return_dict

        end = datetime.now()

        print i

        i = i + 1

        time_diff = end - start
        execution_time = time_diff.total_seconds() * 1000

        query_1_execution_times.append(execution_time)

    print work_place_return

    print 'QUERY 1'
    print query_1_execution_times

    with open('no_sql.csv', 'ab') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=',')
        #for recorded_time in execution_time_array:
        spamwriter.writerow(query_1_execution_times)

#Get interests of all coworkers
def query2(r, people_list):
    query_2_execution_times = []
    
    interest_list = set()

    i = 0
    while i < 30:
    
        start = datetime.now()

        for person in people_list:
            coworkers_set = set()
            workplace_array = ast.literal_eval(r.get('person.{}.work'.format(person)))
            #print 'workplace array ', workplace_array
            for work in workplace_array:
                coworkers = ast.literal_eval(r.get('work.{}'.format(work)))
                coworkers_set.update(coworkers)
                #print 'coworkers ', coworkers_set
                for coworker in coworkers_set:
                    interest_list.update(ast.literal_eval(r.get('person.{}.interest'.format(coworker))))

        end = datetime.now()
        
        time_diff = end - start
        execution_time = time_diff.total_seconds() * 1000

        query_2_execution_times.append(execution_time)
        print i

        i = i + 1

    print 'QUERY 2'
    print query_2_execution_times

    with open('no_sql.csv', 'ab') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=',')
        #for recorded_time in execution_time_array:
        spamwriter.writerow(query_2_execution_times)


#Get interests of all People
def query3(r, people_list):
    query_3_execution_times = []
    return_array = []

    i = 0
    while i < 30:

        start = datetime.now()
        for person in people_list:
            return_array.append(ast.literal_eval(r.get('person.{}.interest'.format(person))))

        end = datetime.now()

        time_diff = end - start
        execution_time = time_diff.total_seconds() * 1000

        query_3_execution_times.append(execution_time)
        print i
        i = i + 1

    print 'QUERY 3'
    print query_3_execution_times

    with open('no_sql.csv', 'ab') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=',')
        #for recorded_time in execution_time_array:
        spamwriter.writerow(query_3_execution_times)


if __name__ == "__main__":
    print 'started?'
    start()
import MySQLdb
from datetime import datetime
import csv

class MySQLPerformance():
    def __init__(self):
        pass

    def start(self):
        i = 0
        people = []
        while i < 1000:
            people.append('Person_{}'.format(i))
            i = i + 1   

        queries = ["SELECT EmployeeTable.Person_ID, EmployeeTable.Work_ID FROM EmployeeTable WHERE EmployeeTable.Work_ID = ANY (SELECT EmployeeTable.Work_ID FROM EmployeeTable WHERE EmployeeTable.Person_ID = '{}');",
                   "SELECT RelationshipTable.Interest_ID, COUNT(RelationshipTable.Interest_ID) FROM RelationshipTable WHERE RelationshipTable.Person_ID = ANY (SELECT EmployeeTable.Person_ID FROM EmployeeTable WHERE EmployeeTable.Work_ID = ANY (SELECT EmployeeTable.Work_ID FROM EmployeeTable WHERE EmployeeTable.Person_ID ='{}')) GROUP BY RelationshipTable.Interest_ID;",
                   "SELECT RelationshipTable.Interest_ID FROM RelationshipTable WHERE RelationshipTable.Person_ID ='{}';"
                  ]

        db = MySQLdb.connect("localhost","test","testtest","TEST")
        #db = psycopg2.connect(database="postgres", user="postgres")
        cursor = db.cursor()

        self.repopulate(cursor)


        for query in queries:
            
            i =0

            print '\n'

            print 'NEW QUERY ', query
        


            execution_time_array = []
            while i < 30:


                db.commit()
                start = datetime.now()
                for person in people:
                    
                    cursor.execute(query.format(person))
                    
                end = datetime.now()
                db.commit()

                print i

                time_diff = end - start
                execution_time = time_diff.total_seconds() * 1000

                execution_time_array.append(execution_time)
                
                i = i + 1

            with open('MySQL_final_logs.csv', 'ab') as csvfile:
                spamwriter = csv.writer(csvfile, delimiter=',')
                #for recorded_time in execution_time_array:
                spamwriter.writerow(execution_time_array)

    def repopulate(self, cursor):
        self.repopulatePersonTable(cursor)
        self.repopulateInterestTable(cursor)
        self.repopulateRelationshipTable(cursor)
        self.repopulateWorkTable(cursor)
        self.repopulateEmployeeTable(cursor)

    def repopulatePersonTable(self, cursor):
        with open ("person_table.sql", "r") as f:
            person_queries = f.readlines()

        for person_query in person_queries:
            #print order_query
            try:
                cursor.execute(person_query)
            except Exception, e:
                #print e
                pass

    def repopulateInterestTable(self, cursor):
        with open ("interest_table.sql", "r") as f:
            interest_queries = f.readlines()

        for interest_query in interest_queries:
            #print order_query
            try:
                cursor.execute(interest_query)
            except Exception, e:
                #print e
                pass

    def repopulateRelationshipTable(self, cursor):
        with open ("relationship_table.sql", "r") as f:
            relationship_queries = f.readlines()

        for relationship_query in relationship_queries:
            #print order_query
            try:
                cursor.execute(relationship_query)
            except Exception, e:
                #print e
                pass

    def repopulateWorkTable(self, cursor):
        with open ("work_table.sql", "r") as f:
            work_queries = f.readlines()

        for work_query in work_queries:
            #print order_query
            try:
                cursor.execute(work_query)
            except Exception, e:
                #print e
                pass

    def repopulateEmployeeTable(self, cursor):
        with open ("employee_table.sql", "r") as f:
            employee_queries = f.readlines()

        for employee_query in employee_queries:
            #print order_query
            try:
                cursor.execute(employee_query)
            except Exception, e:
                #print e
                pass

    def repopulate_order_table(self, cursor):
        with open ("{}/{}_order_table.sql".format(self.size_names[self.size], self.size_names[self.size]), "r") as order_file:
            order_queries = order_file.readlines()

        for order_query in order_queries:
            #print order_query
            try:
                cursor.execute(order_query)
            except Exception, e:
                #print e
                pass


if __name__ == '__main__':
    instance = MySQLPerformance()
    instance.start()
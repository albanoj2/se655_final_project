#import MySQLdb
import psycopg2
from datetime import datetime
import csv



class MySQLPerformance():
    def __init__(self):
        pass

    def start(self):
        queries = ['SELECT * FROM OrderTable;', 
                   'SELECT OrderProductTable.Order_Number, OrderProductTable.Product_ID, OrderProductTable.Count FROM OrderProductTable ORDER BY OrderProductTable.Count;', 
                   'SELECT OrderTable.Client_ID, COUNT(OrderTable.Client_ID) as count FROM OrderTable GROUP BY OrderTable.Client_ID ORDER BY count DESC;',
                   'UPDATE OrderProductTable SET Count = 5 WHERE Count > 5;',
                   "SELECT ProductTable.Description FROM ProductTable WHERE ProductTable.Product_ID = ANY (SELECT OrderProductTable.Product_ID FROM OrderProductTable WHERE OrderProductTable.Order_ID = ANY (SELECT OrderTable.Order_ID FROM OrderTable WHERE OrderTable.Client_ID = ANY (SELECT ClientTable.Client_ID FROM ClientTable WHERE ClientTable.Email = 'Client_0@blah.com')));"]

        self.size = 3
        self.size_names = ["small", "medium", "large", "extra_large"]

        for query in queries:
            #db = MySQLdb.connect("localhost","root","","TEST")
            db = psycopg2.connect(database="postgres", user="postgres")
            cursor = db.cursor()
            i =0

            print '\n'
            print 'NEW QUERY ', query

            execution_time_array = []
            while i < 30:

                self.repopulate(cursor)
                db.commit()

                start = datetime.now()
                cursor.execute(query)
                end = datetime.now()

                db.commit()

                time_diff = end - start
                execution_time = time_diff.total_seconds() * 1000

                execution_time_array.append(execution_time)
                
                i = i + 1

            with open('{}_PostgreSQl_logs.csv'.format(self.size_names[self.size]), 'ab') as csvfile:
                spamwriter = csv.writer(csvfile, delimiter=',')
                #for recorded_time in execution_time_array:
                spamwriter.writerow(execution_time_array)

    def repopulate(self, cursor):
        self.repopulate_order_table(cursor)
        self.repopulate_client_table(cursor)
        self.repopulate_product_table(cursor)
        self.repopulate_order_product_table(cursor)

    def repopulate_order_product_table(self, cursor):
        with open ("{}/{}_order_product_table.sql".format(self.size_names[self.size], self.size_names[self.size]), "r") as order_product_file:
            order_product_queries = order_product_file.readlines()

        for order_product_query in order_product_queries:
            #print order_product_query
            try:
                cursor.execute(order_product_query)
            except Exception, e:
                #print e
                pass     

    def repopulate_product_table(self, cursor):
        with open ("{}/{}_product_table.sql".format(self.size_names[self.size], self.size_names[self.size]), "r") as product_file:
            product_queries = product_file.readlines()

        for product_query in product_queries:
            #print product_query
            try:
                cursor.execute(product_query)
            except Exception, e:
                #print e
                pass

    def repopulate_client_table(self, cursor):
        with open ("{}/{}_client_table.sql".format(self.size_names[self.size], self.size_names[self.size]), "r") as client_file:
            client_queries = client_file.readlines()

        for client_query in client_queries:
            #print client_query
            try:
                cursor.execute(client_query)
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

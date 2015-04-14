import random

#0 = small, 1 = medium , 2 = large, 3 = Extra Large
size = 0
size_names = ["small", "medium", "large", "extra_large"]
product = [10, 100, 1000, 10000]
users = [10, 100, 1000, 10000]
orders = [100, 1000, 10000, 100000]
product_orders = [880, 8800, 88000, 880000]
count_list = [1,2,3,4,5,6,7,8,9,10]

#Generate Products
product_key_list = []
x = 0
while x < product[size]:
    product_key_list.append("Product_{}".format(x))
    x = x+1

with open ("{}/{}_product_table.sql".format(size_names[size], size_names[size]), "wa") as query:
    query.write("DROP TABLE ProductTable;\n")
    query.write("\n")
    query.write("CREATE TABLE ProductTable (Description VARCHAR(200), Product_ID VARCHAR(200), PRIMARY KEY (Product_ID));\n")
    query.write("\n")
    i = 0
    for product_id in product_key_list:
        #Do only when it is the first in the row
        if i%10 == 0:
            query.write("INSERT INTO ProductTable (Description, Product_ID) VALUES ")

        #DO THIS NO MATTER WHAT
        query.write("('blahh','{}')".format(product_id))

        #Do only if it is the last in the row
        if i%10 == 9:
            query.write(";\n")
        else:
            query.write(",")
        i = i+1


#GENERATE USERS
x = 0
client_key_list = []
while x < users[size]:
    client_key_list.append("Client_{}".format(x))
    x = x + 1

with open ("{}/{}_client_table.sql".format(size_names[size], size_names[size]), "wa") as query:
    query.write("DROP TABLE ClientTable;\n")
    query.write("\n")
    query.write("CREATE TABLE ClientTable (Email VARCHAR(200), Address VARCHAR(200), Client_ID VARCHAR(200), PRIMARY KEY (Client_ID));\n")
    query.write("\n")

    i=0
    for client_id in client_key_list:
        #Do only when it is the first in the row
        if i%10 == 0:
            query.write("INSERT INTO ClientTable (Email, Address, Client_ID) VALUES ")

        #DO THIS NO MATTER WHAT
        query.write("('{}@blah.com','{} Address','{}')".format(client_id, client_id, client_id))
        
        #Do only if it is the last in the row
        if i%10 == 9:
            query.write(";\n")
        else:
            query.write(",")
        i = i+1



#GENERATE ORDER
x = 0
order_key_list = []
while x < orders[size]:
    order_key_list.append("Order_{}".format(x))
    x = x + 1

with open("{}/{}_order_table.sql".format(size_names[size], size_names[size]), "wa") as query:
    query.write("DROP TABLE OrderTable;\n")
    query.write("\n")
    query.write("CREATE TABLE OrderTable (Client_ID VARCHAR(200), Order_ID VARCHAR(200), PRIMARY KEY (Order_ID));\n")
    query.write("\n")

    i=0
    for order_id in order_key_list:
        #Do only when it is the first in the row
        if i%10 == 0:
            query.write("INSERT INTO OrderTable (Client_ID, Order_ID) VALUES ")

        #DO THIS NO MATTER WHAT
        query.write("('{}','{}')".format(random.choice(client_key_list), order_id))
        
        #Do only if it is the last in the row
        if i%10 == 9:
            query.write(";\n")
        else:
            query.write(",")
        i = i+1
        

#GENERATE ORDER_PRODUCT
x = 0
product_order_key_list = []
while x < product_orders[size]:
    product_order_key_list.append("Order_Number_{}".format(x))
    x = x + 1

with open("{}/{}_order_product_table.sql".format(size_names[size], size_names[size]), "wa") as query:
    query.write("DROP TABLE OrderProductTable;\n")
    query.write("\n")
    query.write("CREATE TABLE OrderProductTable (Order_Number VARCHAR(200), Order_ID VARCHAR(200), Count INT, Product_ID VARCHAR(200), PRIMARY KEY (Order_Number));\n")
    query.write("\n")

    i=0
    for order_num in product_order_key_list:
        #Do only when it is the first in the row
        if i%10 == 0:
            query.write("INSERT INTO OrderProductTable (Order_Number, Order_ID, Count, Product_ID) VALUES ")

        #DO THIS NO MATTER WHAT
        query.write("('{}','{}','{}','{}')".format(order_num, random.choice(order_key_list), random.choice(count_list), random.choice(product_key_list)))
        
        #Do only if it is the last in the row
        if i%10 == 9:
            query.write(";\n")
        else:
            query.write(",")
        i = i+1





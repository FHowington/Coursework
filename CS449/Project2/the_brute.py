import os
import random

f = open('out.txt', 'w+')
f2 = open('in.txt', 'w+')
f3 = open('success.txt', 'w+')
x = 0
while True:
    s = ""
    for i in range(0, 10):
        s += (chr(random.randint(32, 126)))

    # print(s)
    f2.write(s)
    os.system('./fot3_3 < in.txt > out.txt')
    result = f.read()
    x = x+1
    if x == 100000:
        print("A billion")
        x = 0
    # print("Result is " + result)
    if "Sorry! Not correct!" in result:
        pass
    else:
        f3.write(result)
        f3.write('\n')
        f3.write(s)
        f3.write('\n')
    f2.seek(0)
    f.seek(0)
    f.truncate(0)





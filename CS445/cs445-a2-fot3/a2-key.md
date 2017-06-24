# CS 445 A2 Key

## Group 1 Expressions

### 5 points each (25 total)

    Test: 2+2
    Expected: 4.0

    Test: 3.5*3.5
    Expected: 12.25

    Test: 9.512-65.23
    Expected: -55.718

    Test: 4.444444/2.222222
    Expected: 2.0

    Test: 17.50^2.1
    Expected: 407.73679255970353

## Group 2 Expressions

### 4 points each (16 total)

    Test: 4+2.5*3
    Expected: 11.5

    Test: 4*2.5+3
    Expected: 13.0

    Test: 2+3*5^2*7
    Expected: 527.0

    Test: 3^4-9/3
    Expected: 78.0

## Group 3 Expressions

### 3 points each (12 total)

    Test: (5.0+10.6)
    Expected: 15.6

    Test: [3+(5*4)-(8/3)]*2
    Expected: 40.666666666666664

    Test: (1+[(3-(1.1+1.2))^2])
    Expected: 1.4900000000000002

    Test: [3*5.5]^[18.4-14.2]
    Expected: 129847.19302608373

## Group 4 Expressions

### 2 points each (10 total)

    Test: 2^(2+3*4)
    Expected: 16384.0

    Test: 2*14.5+6/5-(5*8-5/9)
    Expected: -9.244444444444444

    Test: [50*10^2]/(500-35*9^2)
    Expected: -2.1413276231263385

    Test: [1+2/2]^(35-[6*5.1])+4
    Expected: 25.11212657236634

    Test: 10000*[1+.20/12]^(12*4)
    Expected: 22109.15081062563

## Group 5 Expressions

### 1 points each (7 total)

    Test: 4*3*2*4+2*3*4*5-3*9*7*3+2*3*4*4^2
    Expected: 33.0

    Test: ([([2+2]-[3^2])*(0-8)-3]*2^[1/8])
    Expected: 40.34878610861453

    Test: 3+2^4*2-[(2+2)^(5/2)]/3
    Expected: 24.333333333333336

    Test: (1)/[2^3*8]
    Expected: 0.015625

    Test: 3*(2+3*2^7)-[2*4]
    Expected: 1150.0

    Test: 3^4*3.5+3-2*4^2/1.1
    Expected: 257.40909090909093

    Test: (5*8)-[9+4/8-6+4*5^2]/(0-[5*4+6-2/1+5])
    Expected: 43.56896551724138

## Invalid Expressions

### 3 points each (30 total)

    Test: (4+3*2
    Expected: ExpressionError: Open bracket without close bracket

    Test: (4+3*2]
    Expected: ExpressionError: Mismatched close/open brackets

    Test: 2*(1+3*[1+2)-0.5]
    Expected: ExpressionError: Mismatched close/open brackets

    Test: 2*1+3^3)
    Expected: ExpressionError: Close bracket without open bracket

    Test: 2*(2+1)3
    Expected: ExpressionError: Operand following close bracket
              OR 18.0 if implied multiplication is implemented

    Test: 2 * + 3
    Expected: ExpressionError: Two operators in a row

    Test: 2 4 + * 3
    Expected: ExpressionError: Two operands in a row
              OR ExpressionError: Two operators in a row

    Test: * 2 + 3 5
    Expected: ExpressionError: Expression begins with operator
              OR ExpressionError: Two operands in a row

    Test: 3+()5

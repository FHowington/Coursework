/*
 * CS 449 Project 1
 * Forbes Turley
 * Summer 2017
 */
#include <stdio.h>
#include <string.h>
#include <time.h>
int main(){
    /*
     * Creating prototypes and setting up random number generation
     */

    void srand(unsigned int seed);
    time_t time(time_t *t);
    int rand(void);
    srand((unsigned int)time(NULL));
    int i;
    int high = 14;
    int low = 2;
    int dealerHand[20];
    int playerHand[20];
    int playerCards = 0;
    int dealerCards = 1;

    /*Dealer initially*/
    dealerHand[0] = rand() % (high - low + 1) + low;
    dealerHand[1] = rand() % (high - low + 1) + low;
    /*Player initially*/
    playerHand[0] = rand() % (high - low + 1) + low;

    /* Handling the oddball case of the dealer drawing two aces on first deal */
    if(dealerHand[0] == 11 && dealerHand[1] ==1){
        dealerHand[0]=1;
    }

    /*Setting facecards value to 10 for dealer and player*/
    if(playerHand[playerCards]>11){
        playerHand[playerCards] = 10;
    }

    if(dealerHand[0]>11){
        dealerHand[0] = 10;
    }
    if(dealerHand[1]>11){
        dealerHand[1] = 10;
    }

    printf("\nThe dealer:\n? + %d\n",dealerHand[1]);

    int stillPlaying = 1;
    int total;
    int aceLocation;
    int aceFound;

    while(stillPlaying){
        total = 0;
        playerHand[++playerCards]=rand() % (high - low + 1) + low;
        if(playerHand[playerCards]>11){
            playerHand[playerCards] = 10;
        }
        printf("\nYou:\n");	

        aceFound = 0;
        /*Finding an ace, if there are any, and calculating players total*/
        for(i=0;i<playerCards+1;i++){
            total+=playerHand[i];
            if(playerHand[i]==11){
                aceLocation = i;
                aceFound = 1;
            }
            /*Calculation of players hand is reset if an ace is set to a 1*/
            if(total > 21 && aceFound){
                aceFound = 0;
                playerHand[aceLocation] = 1;
                i = 0;
                total = playerHand[0];
            }
        }
        /*Printing the hand*/
        for(i=0;i<playerCards+1;i++){
            if(0 == i){
                printf("%d",playerHand[i]);
            }
            else{
                printf(" + %d",playerHand[i]);
            }
        }



        printf(" = %d",total);

        if(21<total){
            printf(" Busted!\n\nYou busted. Dealer wins.");
            stillPlaying = 0;
        }
        else{
            char ans[15];
            char str1[15];
            char str2[15];

            /*Determining whether the player wants more cards*/
            strcpy(str1,"hit");
            strcpy(str2,"stand");
            printf("\n\nWould you like to \"hit\" or \"stand\"? ");
            scanf("%s",ans);
            if(strcmp(str2,ans) == 0){
                stillPlaying = 0;
            }
        }
    }

    int dealerTotal;
    int dealerPlaying;
    /*Initializing dealerTotal to determine if more cards are needed*/
    dealerTotal = dealerHand[0] + dealerHand[1];
    /*If the player has already buster, no need for dealer to play*/
    if(total < 22){
        if(dealerTotal>16){
            dealerPlaying = 0;
        }
        else{
            dealerPlaying = 1;
        }
        while(dealerPlaying){
            /*Drawing dealer new card and recalculating total*/
            dealerTotal = 0;
            dealerHand[++dealerCards]=rand() % (high - low + 1) + low;
            if(dealerHand[dealerCards]>11){
                dealerHand[dealerCards] = 10;
            }

            aceFound = 0;
            /*Finding ace location and total*/
            for(i=0;i<dealerCards+1;i++){
                dealerTotal+=dealerHand[i];
                if(dealerHand[i]==11){
                    aceLocation = i;
                    aceFound = 1;
                }
                /*If ace is present and dealer has busted, ace is set to 0 and count is restarted*/
                if(dealerTotal > 21 && aceFound){
                    aceFound = 0;
                    dealerHand[aceLocation] = 1;
                    i = 0;
                    dealerTotal = dealerHand[0];
                }
            }
            if(dealerTotal>16){
                dealerPlaying = 0;
            }
        }

        /*Printing out each possible end game sitiuation*/
        if(dealerTotal > 21){
            printf("\nDealer busted with %d. Their hand was:\n",dealerTotal);
        }
        else if(dealerTotal >16){
            if(total > dealerTotal){
                printf("\nThe player wins! The dealer's hand was:\n");

            }
            else if(total == dealerTotal){
                printf("\nYou tied the dealer. Their hand was:\n");
            }
            else{
                printf("\nThe dealer wins! Their hand was:\n");
            }
        }
        /*Dealers hand is output if player did not bust. If player busted, no hand is output*/
        for(i=0;i<dealerCards+1;i++){
            if(i == 0){
                printf("%d",dealerHand[i]);
            }
            else{
                printf(" + %d",dealerHand[i]);
            }
        }
        printf(" = %d",dealerTotal);


    }
    printf("\n");
    return 0;
}


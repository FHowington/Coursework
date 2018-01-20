/*
 * Forbes Turley
 * Driver for graphics library
 * CS1550
 * University of Pittsburgh
 */

#include "library.h"
#include <stdio.h>

// Usage: Driver will first draw two pixels on screen, then draw a series of lines which demonstrates
// that any slope can be produced. Finally, user input is enabled. Using wasd, user can move the lines
// up/down and left/right. Press q to quit.
int main(){
    color_t color;
    color.color = RGB(31,0,0);
    init_graphics();
    void* mapped_screen;
    mapped_screen = new_offscreen_buffer();
    draw_pixel(mapped_screen,20,20,color);
    blit(mapped_screen);
    sleep_ms(500);
    draw_pixel(mapped_screen,639,479,color);
    blit(mapped_screen);
    sleep_ms(500);
    draw_line(mapped_screen,100,100,200,200,color);
    blit(mapped_screen);
    sleep_ms(1000);
    color.color = RGB(0,63,0);
    draw_line(mapped_screen,150,100,150,200,color);
    blit(mapped_screen);
    sleep_ms(1000);
    color.color = RGB(0,0,31);
    draw_line(mapped_screen,100,150,200,150,color);
    blit(mapped_screen);
    sleep_ms(1000);
    color.color = RGB(31,63,31);
    draw_line(mapped_screen,200,100,100,200,color);
    blit(mapped_screen);
    sleep_ms(1000);
    blit(mapped_screen);


    int i = 320;
    int j = 240;

    clear_screen(mapped_screen);
    color.color = RGB(0,63,0);
    draw_line(mapped_screen,0,j,639,j,color);
    color.color = RGB(0,0,31);
    draw_line(mapped_screen,i,0,i,479,color);
    blit(mapped_screen);

    char c;
    while(1){
        c = getkey();
        if(getkey() == 'q')
            break;
        else if(c == 'w'){
            if(j>9)
                j-=10;
        }
        else if(c == 's'){
            if(j<470)
                j+=10;
        }
        else if(c == 'd'){
            if(i<630)
                i+=10;
        }
        else if(c == 'a'){
            if(i>9)
                i-=10;
        }
        clear_screen(mapped_screen);
        color.color = RGB(0,63,0);
        draw_line(mapped_screen,0,j,639,j,color);
        color.color = RGB(0,0,31);
        draw_line(mapped_screen,i,0,i,479,color);
        blit(mapped_screen);
    }
    clear_screen(mapped_screen);
    blit(mapped_screen);
    exit_graphics();
    return 0;
}

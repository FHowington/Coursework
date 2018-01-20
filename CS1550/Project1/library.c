/*  CS 1550 Project 1
 *  Forbes Turley
 *  University of Pittsburgh
 *  9/30/17
 */

#include "library.h"
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <linux/fb.h>
#include <unistd.h>
#include <termios.h>
#include <sys/mman.h>
#include <sys/select.h>
#include <time.h>

// Global variable to which we save the pointer to the mmaped buffer
void* mapped_buffer;
int x_pixels;
int y_pixels;
int total_size;
int buffer_file;

void init_graphics(){
    // Setting up structs to receive screen dimensions
    struct fb_var_screeninfo v_screeninfo; 
    struct fb_fix_screeninfo f_screeninfo;
    struct termios termios_rcvd;

    buffer_file = open("/dev/fb0",O_RDWR);
    ioctl(buffer_file,FBIOGET_VSCREENINFO,&v_screeninfo);
    ioctl(buffer_file,FBIOGET_FSCREENINFO,&f_screeninfo);
    // Requesting screen dimensions
    x_pixels = f_screeninfo.line_length;
    y_pixels = v_screeninfo.yres_virtual;
    total_size = x_pixels * y_pixels;
    // Initializing mmaped region
    mapped_buffer = mmap(NULL,total_size,PROT_WRITE|PROT_READ,MAP_SHARED,buffer_file,0);
    write(1,"\033[2J",8);
    ioctl(0,TCGETS,&termios_rcvd);
    termios_rcvd.c_lflag &= ~(ICANON|ECHO);
    ioctl(0,TCSETS,&termios_rcvd);
}

void sleep_ms(long ms){
    // Sleep_ms sleeps for the requested number of milliseconds
    // If this number is >1000, it carries into the seconds variable of timespec struct
    struct timespec ts;
    ts.tv_sec = ms/1000;
    ts.tv_nsec = (ms - 1000*(ts.tv_sec))*1000000;
    nanosleep(&ts,NULL);
}

void exit_graphics(){
    // Reenabling keypress updates and closing the buffer file
    struct termios termios_rcvd;
    ioctl(0,TCGETS,&termios_rcvd);
    termios_rcvd.c_lflag |= ICANON;
    termios_rcvd.c_lflag |= ECHO;
    ioctl(0,TCSETS,&termios_rcvd);  
    close(buffer_file);
}

char getkey(){
    // Checks to see whether the user has pressed any keys. If so, returns number pressed
    // If not, returns NULL
    // Note that timeout is 0 seconds, so it should instantaneously return the state of the 
    // key buffer
    
    char output = '\0';
    int keystrokes;
    fd_set keybuffer;
    FD_ZERO(&keybuffer);
    FD_SET(0,&keybuffer);
    // Add failure for read and select
    keystrokes = select(1,&keybuffer,NULL,NULL,NULL);
    if(keystrokes != 0){
        read(0,&output,1);
    }
    return output;
}

void* new_offscreen_buffer(){
    return mmap(NULL,total_size,PROT_WRITE|PROT_READ,MAP_PRIVATE|MAP_ANONYMOUS,-1,0);
}

void blit(void *src){
    // Copies every pixel to the mapped_buffer global variable. Note that init graphics
    // must have been called prior to this for this to work
    int i;
    for(i=0;i<total_size;i++){
        ((char*)mapped_buffer)[i] = ((char*)src)[i];
    }
}

void draw_pixel(void *img, int x, int y, color_t color){
    // Sets pixel at specified point to color described by color_t
    *((short*)(img + y*x_pixels + x*2)) = color.color;
}


void draw_line(void *img, int x1, int y1, int x2, int y2, color_t color){
    // Iterates through values x.y dimensions of a line and fills in pixels accordingly
    // Based on Bresenham's algorithm
    // Modified from https://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm

    int dx = x1<x2? (x2-x1):(x1-x2);
    int sx = x1<x2 ? 1 : -1;
    int dy = y1<y2? (y2-y1):(y1-y2);
    int sy = y1<y2 ? 1 : -1; 
    int err = (dx>dy ? dx : -dy)/2, e2;

    while(1){
        draw_pixel(img,x1,y1,color);
        if (x1==x2 && y1==y2) break;
        e2 = err;
        if (e2 >-dx) { err -= dy; x1 += sx; }
        if (e2 < dy) { err += dx; y1 += sy; }
    }
}

void clear_screen(void *img){
    // Similar to blit, sets every pixel in *img to a blank color (off)
    int i,j;
    color_t color;
    color.color = RGB(0,0,0);
    for(i=0;i<(x_pixels/2);i++){
        for(j=0;j<y_pixels;j++)
            draw_pixel(img,i,j,color);
    }
}


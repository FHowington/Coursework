#define RGB(a,b,c) ((a<<11 & 0xF800)|(b<<5 & 0x07E0) | (c & 0x001F))

typedef struct color_t{
        short color;
} color_t;

void init_graphics();
void exit_graphics();
char getkey();
void sleep_ms(long ms);
void clear_screen(void *img);
void draw_pixel(void *img,int x,int y,color_t color);
void draw_line(void *img,int x1,int y1,int x2,int y2,color_t color);
void *new_offscreen_buffer();
void blit(void *src);

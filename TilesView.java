package com.example.colortiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

class Tile{
    //TODO: зачем столько строк
    int color;
    int left;
    int right;
    int top;
    int bottom;
    
    //TODO: Такой конструктор класса генерируется автоматически и писать его бессмысленно
    Tile(){

    }

    Tile(int l, int t, int r, int b, int c){
        color = c;
        left = l;
        right = r;
        bottom = b;
        top = t;
    }
    
    //TODO: для красоты нужно указывать индедификатор доступа
    int getColor(){
        return color;
    }
    //TODO: Аналагично
    void setColor(int c){
        color = c;
    }
}

public class TilesView extends View {
    //TODO: Можно объявить все в одной строке
    boolean flag = true;
    int rowNum = 4;
    int colNum = 4;
    int outline = 20;

    Tile[][] tiles = new Tile[4][4];
    int darkColor = Color.parseColor("#E04D22");
    int lightColor = Color.parseColor("#5C16E0");
    int width;
    int height;

    public TilesView(Context context){
        super(context);
    }

    public TilesView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas){
        width = canvas.getWidth();
        height = canvas.getHeight();
        
        //TODO: Пробелы между оператором деления
        int t_width = width/rowNum;
        int t_height = height/colNum;

        Paint light = new Paint();
        light.setColor(lightColor);
        Paint dark = new Paint();
        dark.setColor(darkColor);

        light.setStyle(Paint.Style.FILL);
        dark.setStyle(Paint.Style.FILL);

        for(int i = 0; i < rowNum; i++){
            for (int j = 0; j < colNum; j++) {
                //TODO: Пробелы между оператором деления
                int left = j*t_width;
                int top = i*t_height;
                int right = left + t_width;
                int bottom = top + t_height;

                Rect tile = new Rect();
                //TODO: Пробелы
                tile.set(left+outline, top+outline, right-outline, bottom-outline);
                
                int color;
                if (flag) {
                    
                    //TODO: Два раза идет бессмысленная отрисовка одного и того же
                    if (Math.random() > 0.5){
                        canvas.drawRect(tile, light);
                        color = 1;
                    } else {
                        canvas.drawRect(tile, dark);
                        color = 0;
                    }if (Math.random() > 0.5){
                        canvas.drawRect(tile, light);
                        color = 1;
                    } else {
                        canvas.drawRect(tile, dark);
                        color = 0;
                    }
                    tiles[i][j] = new Tile(left, top, right, bottom, color);
                } else {
                    color = tiles[i][j].getColor();
                    //TODO: Тут не нужно менять цвет? Так же можно сделать тернарник
                    if (color == 0){
                        canvas.drawRect(tile, light);
                    } else {
                        canvas.drawRect(tile, dark);
                        color = 0;
                    }
                }
            }
        }
        //TODO: flag = !flag
        if (flag == true) {
            flag = false;
        }
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < rowNum; i++) {
                for (int j = 0; j < colNum; j++){
                    
                    //TODO: Запихать все в один if
                    if (tiles[i][j].left < x && tiles[i][j].right > x){
                        if (tiles[i][j].top < y && tiles[i][j].bottom > y){

                            int k = i, m = j;

                            for (int ii = 0; ii < rowNum; ii++) {
                                for (int jj = 0; jj < colNum; jj++) {

                                    if (ii == k || jj == m){
                                        //TODO: tiles[ii][jj].setColor((tiles[ii][jj].getColot() + 1) % 2);
                                        if (tiles[ii][jj].getColor() == 1)
                                            tiles[ii][jj].setColor(0);
                                        else
                                            tiles[ii][jj].setColor(1);
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        invalidate();
        return true;
    }
}

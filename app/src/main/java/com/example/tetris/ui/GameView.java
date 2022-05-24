package com.example.tetris.ui;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import com.example.tetris.game_field.Background;

import androidx.annotation.Nullable;

import java.util.HashSet;

public class GameView extends View {
    int type = 0;
    int turn_count = 0;
    private static int score = 0;

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameView.score = score;
    }

    Background background = new Background();
    Point[] current_element = new Point[4];

    public GameView(Context context) {
        super(context);

    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        types_component();
        t.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        draw(canvas, background);

    }

    public void draw(Canvas canvas, Background background) {
        Paint p = new Paint();
        for (int i = 0; i < background.array.length; i++) {
            for (int j = 0; j < background.array[i].length; j++) {
                switch (background.array[i][j].type) {
                    case (0):
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(3);
                        p.setColor(Color.argb(250, 25, 149, 173));
                        break;
                    case (1):
                        p.setStyle(Paint.Style.FILL);
                        p.setColor(Color.argb(250, 72, 151, 216));
                        break;
                    case (2):
                        p.setStyle(Paint.Style.FILL);
                        p.setColor(Color.argb(255, 255, 219, 92));
                        break;
                    case (3):
                        p.setStyle(Paint.Style.FILL);
                        p.setColor(Color.argb(255, 250, 110, 89));
                        break;
                    case (4):
                        p.setStyle(Paint.Style.FILL);
                        p.setColor(Color.argb(255, 248, 160, 85));
                        break;
                }
                final RectF rect = new RectF();
                rect.set(background.array[i][j].left, background.array[i][j].top, background.array[i][j].right, background.array[i][j].bottom);
                canvas.drawRoundRect(rect, 10, 10, p);
                p.setTextSize(60);
                p.setStyle(Paint.Style.FILL);
                p.setColor(Color.argb(250, 25, 149, 173));
                canvas.drawText("Лучший результат:"+ MainActivity.getScore()+ "  Баллы:" + score, 0, 50, p);
            }
        }
    }

    CountDownTimer t = new CountDownTimer(Long.MAX_VALUE, 1000) {
        @Override
        public void onTick(long l) {
            down();
        }

        @Override
        public void onFinish() {

        }
    };

    void set_main_points(int x3, int x4, int y3, int y4) {
        current_element[0] = new Point(3, 0);
        current_element[1] = new Point(4, 0);
        current_element[2] = new Point(x3, y3);
        current_element[3] = new Point(x4, y4);
    }

    void check_line() {
        HashSet<Integer> lines = new HashSet<>();
        lines.add(current_element[0].y);
        lines.add(current_element[1].y);
        lines.add(current_element[2].y);
        lines.add(current_element[3].y);
        for (int i = 0; i < background.array.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (background.array[i][current_element[j].y].type == 0) {
                    lines.remove(current_element[j].y);
                }
            }
        }
        if (!lines.isEmpty()) {
            for (Integer j : lines) {
                for (int a = j; a > 0; a--) {
                    for (int i = 0; i < background.array.length; i++) {
                        background.array[i][a].type = background.array[i][a - 1].type;
                    }
                }
                score++;
                if (score>MainActivity.getScore())
                    MainActivity.update_result(score);
            }
        }
    }

    void types_component() {
        int temp = type;
        while (temp == type) {
            temp = 1 + (int) (Math.random() * 4);
        }
        type = temp;
        turn_count = 0;
        switch (type) {
            case (1):
                background.array[3][0].type = 1;
                background.array[4][0].type = 1;
                background.array[5][0].type = 1;
                background.array[6][0].type = 1;
                set_main_points(5, 6, 0, 0);
                break;
            case (2):
                background.array[3][0].type = 2;
                background.array[4][0].type = 2;
                background.array[5][1].type = 2;
                background.array[5][0].type = 2;
                set_main_points(5, 5, 1, 0);
                break;
            case (3):
                background.array[3][0].type = 3;
                background.array[4][0].type = 3;
                background.array[4][1].type = 3;
                background.array[5][1].type = 3;
                set_main_points(4, 5, 1, 1);
                break;
            case (4):
                background.array[3][0].type = 4;
                background.array[4][0].type = 4;
                background.array[4][1].type = 4;
                background.array[5][0].type = 4;
                set_main_points(4, 5, 1, 0);
                break;
            default:
                break;
        }
    }

    public void left() {
        boolean go_left = false;
        switch (type) {
            case 1:
                if (background.array[current_element[0].x][current_element[0].y].left != 0) {
                    if (turn_count == 0 && background.array[current_element[0].x - 1][current_element[0].y].type == 0) {
                        go_left = true;
                    } else if ((turn_count == 1) && background.array[current_element[0].x - 1][current_element[0].y].type == 0 &&
                            background.array[current_element[1].x - 1][current_element[1].y].type == 0 &&
                            background.array[current_element[2].x - 1][current_element[2].y].type == 0 &&
                            background.array[current_element[3].x - 1][current_element[3].y].type == 0) {
                        go_left = true;
                    }
                }
                break;
            case 2:
                if (background.array[current_element[0].x][current_element[0].y].left != 0) {
                    if (turn_count == 0 && background.array[current_element[0].x - 1][current_element[0].y].type == 0 &&
                            background.array[current_element[2].x - 1][current_element[2].y].type == 0) {
                        go_left = true;
                    } else if (turn_count == 3 && background.array[current_element[0].x - 1][current_element[0].y].type == 0 &&
                            background.array[current_element[1].x - 1][current_element[1].y].type == 0 &&
                            background.array[current_element[3].x - 1][current_element[3].y].type == 0) {
                        go_left = true;
                    }
                }
                if (background.array[current_element[2].x][current_element[2].y].left != 0) {
                    if (turn_count == 1 && background.array[current_element[0].x - 1][current_element[0].y].type == 0 &&
                            background.array[current_element[1].x - 1][current_element[1].y].type == 0 &&
                            background.array[current_element[2].x - 1][current_element[2].y].type == 0) {
                        go_left = true;
                    } else if (turn_count == 2 && background.array[current_element[2].x - 1][current_element[2].y].type == 0 &&
                            background.array[current_element[3].x - 1][current_element[3].y].type == 0) {
                        go_left = true;
                    }
                }
                break;
            case 3:
                if (background.array[current_element[0].x][current_element[0].y].left != 0) {
                    if (turn_count == 0 && background.array[current_element[0].x - 1][current_element[0].y].type == 0 &&
                            background.array[current_element[2].x - 1][current_element[2].y].type == 0) {
                        go_left = true;
                    } else if (turn_count == 3 && background.array[current_element[0].x - 1][current_element[0].y].type == 0 &&
                            background.array[current_element[1].x - 1][current_element[1].y].type == 0 &&
                            background.array[current_element[3].x - 1][current_element[3].y].type == 0) {
                        go_left = true;
                    }
                }
                if (background.array[current_element[3].x][current_element[3].y].left != 0) {
                    if (turn_count == 1 && background.array[current_element[0].x - 1][current_element[0].y].type == 0 &&
                            background.array[current_element[2].x - 1][current_element[2].y].type == 0 &&
                            background.array[current_element[3].x - 1][current_element[3].y].type == 0) {
                        go_left = true;
                    } else if (turn_count == 2 && background.array[current_element[1].x - 1][current_element[1].y].type == 0 &&
                            background.array[current_element[3].x - 1][current_element[3].y].type == 0) {
                        go_left = true;
                    }
                }
                break;
            case 4:
                if (background.array[current_element[0].x][current_element[0].y].left != 0) {
                    if (turn_count == 0 && background.array[current_element[0].x - 1][current_element[0].y].type == 0 &&
                            background.array[current_element[2].x - 1][current_element[2].y].type == 0) {
                        go_left = true;
                    } else if (turn_count == 3 && background.array[current_element[0].x - 1][current_element[0].y].type == 0 &&
                            background.array[current_element[1].x - 1][current_element[1].y].type == 0 &&
                            background.array[current_element[3].x - 1][current_element[3].y].type == 0) {
                        go_left = true;
                    }
                }
                if (background.array[current_element[2].x][current_element[2].y].left != 0) {
                    if (turn_count == 1 && background.array[current_element[0].x - 1][current_element[0].y].type == 0 &&
                            background.array[current_element[2].x - 1][current_element[2].y].type == 0 &&
                            background.array[current_element[3].x - 1][current_element[3].y].type == 0) {
                        go_left = true;
                    }
                }
                if (background.array[current_element[3].x][current_element[3].y].left != 0) {
                    if (turn_count == 2 && background.array[current_element[2].x - 1][current_element[2].y].type == 0 &&
                            background.array[current_element[3].x - 1][current_element[3].y].type == 0) {
                        go_left = true;
                    }
                }
                break;
            default:
                break;
        }
        if (go_left) {
            for (Point point : current_element) {
                background.array[point.x][point.y].type = 0;
                point.x--;
            }
            for (Point point : current_element) {
                background.array[point.x][point.y].type = type;
            }
        }
        invalidate();
    }

    public void down() {
        boolean go_down = false;
        switch (type) {
            case 1:
                if (current_element[0].y < background.array[0].length - 1) {
                    if (turn_count == 0 && background.array[current_element[0].x][current_element[0].y + 1].type == 0 &&
                            background.array[current_element[1].x][current_element[1].y + 1].type == 0 &&
                            background.array[current_element[2].x][current_element[2].y + 1].type == 0 &&
                            background.array[current_element[3].x][current_element[3].y + 1].type == 0) {
                        go_down = true;
                    }
                }
                if (current_element[3].y < background.array[0].length - 1) {
                    if (turn_count == 1 && background.array[current_element[3].x][current_element[3].y + 1].type == 0) {
                        go_down = true;
                    }
                }
                break;
            case 2:
                if (current_element[0].y < background.array[0].length - 1) {
                    if (turn_count == 2 && background.array[current_element[0].x][current_element[0].y + 1].type == 0 &&
                            background.array[current_element[1].x][current_element[1].y + 1].type == 0 &&
                            background.array[current_element[3].x][current_element[3].y + 1].type == 0) {
                        go_down = true;
                    } else if (turn_count == 3 && background.array[current_element[0].x][current_element[0].y + 1].type == 0 &&
                            background.array[current_element[2].x][current_element[2].y + 1].type == 0) {
                        go_down = true;
                    }
                }
                if (current_element[2].y < background.array[0].length - 1) {
                    if (turn_count == 0 && background.array[current_element[0].x][current_element[0].y + 1].type == 0 &&
                            background.array[current_element[1].x][current_element[1].y + 1].type == 0 &&
                            background.array[current_element[2].x][current_element[2].y + 1].type == 0) {
                        go_down = true;
                    } else if (turn_count == 1 && background.array[current_element[2].x][current_element[2].y + 1].type == 0 &&
                            background.array[current_element[3].x][current_element[3].y + 1].type == 0) {
                        go_down = true;
                    }
                }
                break;
            case 3:
                if (current_element[0].y < background.array[0].length - 1) {
                    if (turn_count == 3 && background.array[current_element[0].x][current_element[0].y + 1].type == 0 &&
                            background.array[current_element[2].x][current_element[2].y + 1].type == 0) {
                        go_down = true;
                    } else if (turn_count == 2 && background.array[current_element[0].x][current_element[0].y + 1].type == 0 &&
                            background.array[current_element[1].x][current_element[1].y + 1].type == 0 &&
                            background.array[current_element[3].x][current_element[3].y + 1].type == 0) {
                        go_down = true;
                    }
                }
                if (current_element[3].y < background.array[0].length - 1) {
                    if (turn_count == 0 && background.array[current_element[0].x][current_element[0].y + 1].type == 0 &&
                            background.array[current_element[2].x][current_element[2].y + 1].type == 0 &&
                            background.array[current_element[3].x][current_element[3].y + 1].type == 0) {
                        go_down = true;
                    } else if (turn_count == 1 && background.array[current_element[1].x][current_element[1].y + 1].type == 0 &&
                            background.array[current_element[3].x][current_element[3].y + 1].type == 0) {
                        go_down = true;
                    }
                }
                break;
            case 4:
                if (current_element[0].y < background.array[0].length - 1) {
                    if (turn_count == 3 && background.array[current_element[0].x][current_element[0].y + 1].type == 0 &&
                            background.array[current_element[2].x][current_element[2].y + 1].type == 0) {
                        go_down = true;
                    } else if (turn_count == 2 && background.array[current_element[0].x][current_element[0].y + 1].type == 0 &&
                            background.array[current_element[1].x][current_element[1].y + 1].type == 0 &&
                            background.array[current_element[3].x][current_element[3].y + 1].type == 0) {
                        go_down = true;
                    }
                }
                if (current_element[3].y < background.array[0].length - 1) {
                    if (turn_count == 1 && background.array[current_element[3].x][current_element[3].y + 1].type == 0 &&
                            background.array[current_element[2].x][current_element[2].y + 1].type == 0) {
                        go_down = true;
                    }
                }
                if (current_element[2].y < background.array[0].length - 1) {
                    if (turn_count == 0 && background.array[current_element[0].x][current_element[0].y + 1].type == 0 &&
                            background.array[current_element[2].x][current_element[2].y + 1].type == 0 &&
                            background.array[current_element[3].x][current_element[3].y + 1].type == 0) {
                        go_down = true;
                    }
                }
                break;
            default:
                break;
        }
        if (go_down) {
            for (Point point : current_element) {
                background.array[point.x][point.y].type = 0;
                point.y++;
            }
            for (Point point : current_element) {
                background.array[point.x][point.y].type = type;
            }
        } else {
            check_line();
            boolean new_element = true;
            for (int i = 0; i < 4; i++) {
                if (current_element[i].y== 0) {
                    new_element = false;
                    Game.getInstance().Message();
                    t.cancel();
                    break;
                }
            }
            if (new_element)
                types_component();
        }
        invalidate();

    }

    public void right() {
        boolean go_right = false;
        switch (type) {
            case 1:
                if (current_element[3].x < background.array.length - 1) {
                    if (turn_count == 0 && background.array[current_element[3].x + 1][current_element[3].y].type == 0) {
                        go_right = true;
                    } else if (turn_count == 1 && background.array[current_element[0].x + 1][current_element[0].y].type == 0 &&
                            background.array[current_element[1].x + 1][current_element[1].y].type == 0 &&
                            background.array[current_element[2].x + 1][current_element[2].y].type == 0 &&
                            background.array[current_element[3].x + 1][current_element[3].y].type == 0) {
                        go_right = true;
                    }
                }
                break;
            case 2:
                if (current_element[2].x < background.array.length - 1) {
                    if (turn_count == 0 && background.array[current_element[3].x + 1][current_element[3].y].type == 0 &&
                            background.array[current_element[2].x + 1][current_element[2].y].type == 0) {
                        go_right = true;
                    } else if (turn_count == 3 && background.array[current_element[2].x + 1][current_element[2].y].type == 0 &&
                            background.array[current_element[1].x + 1][current_element[1].y].type == 0 &&
                            background.array[current_element[0].x + 1][current_element[0].y].type == 0) {
                        go_right = true;
                    }
                }
                if (current_element[0].x < background.array.length - 1) {
                    if (turn_count == 1 && background.array[current_element[0].x + 1][current_element[0].y].type == 0 &&
                            background.array[current_element[1].x + 1][current_element[1].y].type == 0 &&
                            background.array[current_element[3].x + 1][current_element[3].y].type == 0) {
                        go_right = true;
                    } else if (turn_count == 2 && background.array[current_element[2].x + 1][current_element[2].y].type == 0 &&
                            background.array[current_element[0].x + 1][current_element[0].y].type == 0) {
                        go_right = true;
                    }
                }
                break;
            case 3:
                if (current_element[3].x < background.array.length - 1) {
                    if (turn_count == 0 && background.array[current_element[1].x + 1][current_element[1].y].type == 0 &&
                            background.array[current_element[3].x + 1][current_element[3].y].type == 0) {
                        go_right = true;
                    } else if (turn_count == 3 && background.array[current_element[0].x + 1][current_element[0].y].type == 0 &&
                            background.array[current_element[2].x + 1][current_element[2].y].type == 0 &&
                            background.array[current_element[3].x + 1][current_element[3].y].type == 0) {
                        go_right = true;
                    }
                }
                if (current_element[0].x < background.array.length - 1) {
                    if (turn_count == 1 && background.array[current_element[0].x + 1][current_element[0].y].type == 0 &&
                            background.array[current_element[1].x + 1][current_element[1].y].type == 0 &&
                            background.array[current_element[3].x + 1][current_element[3].y].type == 0) {
                        go_right = true;
                    } else if (turn_count == 2 && background.array[current_element[2].x + 1][current_element[2].y].type == 0 &&
                            background.array[current_element[0].x + 1][current_element[0].y].type == 0) {
                        go_right = true;
                    }
                    break;
                }
            case 4:
                if (current_element[3].x < background.array.length - 1) {
                    if (turn_count == 0 && background.array[current_element[3].x + 1][current_element[3].y].type == 0 &&
                            background.array[current_element[2].x + 1][current_element[2].y].type == 0) {
                        go_right = true;
                    } else if (turn_count == 1 && background.array[current_element[0].x - 1][current_element[0].y].type == 0 &&
                            background.array[current_element[1].x + 1][current_element[1].y].type == 0 &&
                            background.array[current_element[3].x + 1][current_element[3].y].type == 0) {
                        go_right = true;
                    }
                }
                if (current_element[2].x < background.array.length - 1) {
                    if (turn_count == 3 && background.array[current_element[0].x + 1][current_element[0].y].type == 0 &&
                            background.array[current_element[2].x + 1][current_element[2].y].type == 0 &&
                            background.array[current_element[3].x + 1][current_element[3].y].type == 0) {
                        go_right = true;
                    }
                }
                if (current_element[0].x < background.array.length - 1) {
                    if (turn_count == 2 && background.array[current_element[2].x + 1][current_element[2].y].type == 0 &&
                            background.array[current_element[0].x + 1][current_element[0].y].type == 0) {
                        go_right = true;
                    }

                }
                break;
            default:
                break;
        }
        if (go_right) {
            for (Point point : current_element) {
                background.array[point.x][point.y].type = 0;
                point.x++;
            }
            for (Point point : current_element) {
                background.array[point.x][point.y].type = type;
            }
        }
        invalidate();
    }

    public void turn() {
        if (type == 1) {
            switch (turn_count) {
                case (0):
                    if (current_element[1].y - 1 < 0 || current_element[1].y + 2 > background.array[0].length - 1 ||
                            background.array[current_element[1].x][current_element[1].y - 1].type != 0 ||
                            background.array[current_element[1].x][current_element[1].y + 1].type != 0 ||
                            background.array[current_element[1].x][current_element[1].y + 2].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x++;
                    current_element[2].x--;
                    current_element[3].x -= 2;
                    current_element[0].y--;
                    current_element[2].y++;
                    current_element[3].y += 2;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 1;
                    break;
                case (1):
                    if (current_element[1].x - 1 < 0 || current_element[1].x + 2 > background.array.length - 1 ||
                            background.array[current_element[1].x - 1][current_element[1].y].type != 0 ||
                            background.array[current_element[1].x + 1][current_element[1].y].type != 0 ||
                            background.array[current_element[1].x + 2][current_element[1].y].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x--;
                    current_element[2].x++;
                    current_element[3].x += 2;
                    current_element[0].y++;
                    current_element[2].y--;
                    current_element[3].y -= 2;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 0;
                    break;
                default:
                    break;
            }

        } else if (type == 2) {
            switch (turn_count) {
                case (0):
                    if (current_element[3].y - 2 < 0 ||
                            background.array[current_element[3].x][current_element[3].y - 1].type != 0 ||
                            background.array[current_element[3].x][current_element[3].y - 2].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x++;
                    current_element[2].x -= 2;
                    current_element[3].x--;
                    current_element[0].y--;
                    current_element[3].y++;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 1;

                    break;
                case (1):
                    if (current_element[3].x + 2 > background.array.length - 1 ||
                            background.array[current_element[3].x + 1][current_element[3].y].type != 0 ||
                            background.array[current_element[3].x + 2][current_element[3].y].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x++;
                    current_element[3].x--;
                    current_element[0].y++;
                    current_element[2].y -= 2;
                    current_element[3].y--;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 2;
                    break;
                case (2):
                    if (current_element[3].y + 2 > background.array[0].length - 1 ||
                            background.array[current_element[3].x][current_element[3].y + 1].type != 0 ||
                            background.array[current_element[3].x][current_element[3].y + 2].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x--;
                    current_element[2].x += 2;
                    current_element[3].x++;
                    current_element[0].y++;
                    current_element[3].y--;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 3;

                    break;
                case (3):
                    if (current_element[3].x - 2 < 0 ||
                            background.array[current_element[3].x - 1][current_element[3].y].type != 0 ||
                            background.array[current_element[3].x - 2][current_element[3].y].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x--;
                    current_element[3].x++;
                    current_element[0].y--;
                    current_element[2].y += 2;
                    current_element[3].y++;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 0;
                    break;
                default:
                    break;
            }
        } else if (type == 3) {
            switch (turn_count) {
                case (0):
                    if (current_element[1].y - 1 < 0 || current_element[2].y + 1 > background.array[0].length - 1 ||
                            background.array[current_element[1].x][current_element[1].y - 1].type != 0 ||
                            background.array[current_element[0].x][current_element[0].y + 1].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x++;
                    current_element[2].x--;
                    current_element[3].x -= 2;
                    current_element[0].y--;
                    current_element[2].y--;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 1;

                    break;
                case (1):
                    if (current_element[0].x - 1 < 0 || current_element[1].x + 1 > background.array.length - 1 ||
                            background.array[current_element[1].x + 1][current_element[1].y].type != 0 ||
                            background.array[current_element[0].x - 1][current_element[0].y].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x--;
                    current_element[2].x++;
                    current_element[3].x += 2;
                    current_element[0].y++;
                    current_element[2].y++;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 0;

                    break;
                default:
                    break;
            }
        } else if (type == 4) {
            switch (turn_count) {
                case (0):
                    if (current_element[1].y - 1 < 0 ||
                            background.array[current_element[1].x][current_element[1].y - 1].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x++;
                    current_element[2].x--;
                    current_element[3].x--;
                    current_element[0].y--;
                    current_element[2].y--;
                    current_element[3].y++;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 1;
                    break;
                case (1):
                    if (current_element[1].x + 1 > background.array.length - 1 ||
                            background.array[current_element[1].x + 1][current_element[1].y].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x++;
                    current_element[2].x++;
                    current_element[3].x--;
                    current_element[0].y++;
                    current_element[2].y--;
                    current_element[3].y--;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 2;
                    break;
                case (2):
                    if (current_element[1].y + 1 > background.array[0].length ||
                            background.array[current_element[1].x][current_element[1].y + 1].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x--;
                    current_element[2].x++;
                    current_element[3].x++;
                    current_element[0].y++;
                    current_element[2].y++;
                    current_element[3].y--;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 3;
                    break;
                case (3):
                    if (current_element[1].x - 1 < 0 ||
                            background.array[current_element[1].x - 1][current_element[1].y].type != 0)
                        break;
                    background.array[current_element[0].x][current_element[0].y].type = 0;
                    background.array[current_element[2].x][current_element[2].y].type = 0;
                    background.array[current_element[3].x][current_element[3].y].type = 0;
                    current_element[0].x--;
                    current_element[2].x--;
                    current_element[3].x++;
                    current_element[0].y--;
                    current_element[2].y++;
                    current_element[3].y++;
                    background.array[current_element[0].x][current_element[0].y].type = type;
                    background.array[current_element[2].x][current_element[2].y].type = type;
                    background.array[current_element[3].x][current_element[3].y].type = type;
                    turn_count = 0;
                    break;
                default:
                    break;
            }
        }
        invalidate();
    }

}
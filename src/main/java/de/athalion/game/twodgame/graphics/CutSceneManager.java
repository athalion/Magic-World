package de.athalion.game.twodgame.graphics;

import de.athalion.game.twodgame.graphics.ui.UI;
import de.athalion.game.twodgame.main.GamePanel;
import de.athalion.game.twodgame.main.GameState;
import de.athalion.game.twodgame.sound.SoundPlayer;
import de.athalion.game.twodgame.utility.RenderUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CutSceneManager {

    GamePanel gamePanel;
    UI ui;
    Graphics2D g2;

    float alpha;
    int timer;
    public int scenePhase;

    //images
    BufferedImage[] scene0 = new BufferedImage[9];

    public CutSceneManager(GamePanel gamePanel, UI ui) {

        this.gamePanel = gamePanel;
        this.ui = ui;

        scene0[0] = ui.loadImage("/cutScene/images/scene1/1");
        scene0[1] = ui.loadImage("/cutScene/images/scene1/2");
        scene0[2] = ui.loadImage("/cutScene/images/scene1/3");
        scene0[3] = ui.loadImage("/cutScene/images/scene1/4");
        scene0[4] = ui.loadImage("/cutScene/images/scene1/5");
        scene0[5] = ui.loadImage("/cutScene/images/scene1/6");
        scene0[6] = ui.loadImage("/cutScene/images/scene1/7");
        scene0[7] = ui.loadImage("/cutScene/images/scene1/8");
        scene0[8] = ui.loadImage("/cutScene/images/scene1/9");

    }

    public void playCutScene(int index, Graphics2D g2) {

        this.g2 = g2;

        gamePanel.gameState = GameState.CUTSCENE;
        timer = 0;

        switch (index) {

            case 0:
                scene0();
                break;

        }

    }

    public void scene0() {

        if (scenePhase == 0) {

            alpha += 0.02f;
            if (alpha > 1f) {
                alpha = 1f;
            }
            fillScreenBlack(alpha);

            if (alpha == 1f) {
                scenePhase++;
            }

        }

        if (scenePhase == 1) {

            double ratio = (double) gamePanel.screenHeight / scene0[0].getHeight();
            int width = (int) (scene0[0].getWidth() * ratio);
            int height = (int) (scene0[0].getHeight() * ratio);

            g2.drawImage(scene0[0], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha -= 0.02f;
            if (alpha < 0f) {
                alpha = 0f;
            }

            fillScreenBlack(alpha);

            if (alpha == 0f) {
                ui.drawSimpleDialog(0, -1, "In einem kleinen Dorf, irgendwo im Wald, lebten einst sieben sehr mächtige magische Wesen. Sie wachten über Angrothea, auch wenn das keiner wusste. Es waren glückliche Zeiten in Angrothea und alle lebten in Frieden zusammen.", 32F, true);
                if (gamePanel.keyHandler.enterPressed) {
                    scenePhase++;
                    gamePanel.keyHandler.enterPressed = false;
                }
            } else gamePanel.keyHandler.enterPressed = false;

        }

        if (scenePhase == 2) {

            fillScreenBlack(1F);

            double ratio = (double) gamePanel.screenHeight / scene0[0].getHeight();
            int width = (int) (scene0[0].getWidth() * ratio);
            int height = (int) (scene0[0].getHeight() * ratio);

            g2.drawImage(scene0[0], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha += 0.02f;
            if (alpha > 1f) {
                alpha = 1f;
            }

            fillScreenBlack(alpha);

            if (alpha == 1f) {
                scenePhase++;
            }

        }

        if (scenePhase == 3) {

            double ratio = (double) gamePanel.screenHeight / scene0[1].getHeight();
            int width = (int) (scene0[1].getWidth() * ratio);
            int height = (int) (scene0[1].getHeight() * ratio);

            g2.drawImage(scene0[1], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha -= 0.02f;
            if (alpha < 0f) {
                alpha = 0f;
            }

            fillScreenBlack(alpha);

            if (alpha == 0f) {
                ui.drawSimpleDialog(0, -1, "Doch eines Tages erschien ein rotes Licht am Himmel und wurde immer heller und heller.", 32F, true);
                if (gamePanel.keyHandler.enterPressed) {
                    scenePhase++;
                    gamePanel.keyHandler.enterPressed = false;
                }
            } else gamePanel.keyHandler.enterPressed = false;

        }

        if (scenePhase == 4) {

            fillScreenBlack(1F);

            double ratio = (double) gamePanel.screenHeight / scene0[1].getHeight();
            int width = (int) (scene0[1].getWidth() * ratio);
            int height = (int) (scene0[1].getHeight() * ratio);

            g2.drawImage(scene0[1], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha += 0.02f;
            if (alpha > 1f) {
                alpha = 1f;
            }

            fillScreenBlack(alpha);

            if (alpha == 1f) {
                scenePhase++;
            }

        }

        if (scenePhase == 5) {

            double ratio = (double) gamePanel.screenHeight / scene0[2].getHeight();
            int width = (int) (scene0[2].getWidth() * ratio);
            int height = (int) (scene0[2].getHeight() * ratio);

            g2.drawImage(scene0[2], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha -= 0.02f;
            if (alpha < 0f) {
                alpha = 0f;
            }

            fillScreenBlack(alpha);

            if (alpha == 0f) {
                ui.drawSimpleDialog(0, -1, "Bis eines der über Angrothea wachenden Wesen sich ihm entgegenstellte. Doch es wurde einfach von dem Licht verschlungen und wart seitdem nie wieder gesehen.", 32F, true);
                if (gamePanel.keyHandler.enterPressed) {
                    scenePhase++;
                    gamePanel.keyHandler.enterPressed = false;
                }
            } else gamePanel.keyHandler.enterPressed = false;

        }

        if (scenePhase == 6) {

            fillScreenBlack(1F);

            double ratio = (double) gamePanel.screenHeight / scene0[2].getHeight();
            int width = (int) (scene0[2].getWidth() * ratio);
            int height = (int) (scene0[2].getHeight() * ratio);

            g2.drawImage(scene0[2], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha += 0.02f;
            if (alpha > 1f) {
                alpha = 1f;
            }

            fillScreenBlack(alpha);

            if (alpha == 1f) {
                scenePhase++;
            }

        }

        if (scenePhase == 7) {

            double ratio = (double) gamePanel.screenHeight / scene0[3].getHeight();
            int width = (int) (scene0[3].getWidth() * ratio);
            int height = (int) (scene0[3].getHeight() * ratio);

            g2.drawImage(scene0[3], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha -= 0.02f;
            if (alpha < 0f) {
                alpha = 0f;
            }

            fillScreenBlack(alpha);

            if (alpha == 0f) {
                ui.drawSimpleDialog(0, -1, "Daraufhin zogen die sechs verbliebenen Wesen los. Sie gingen zu der Feste Thaural, in der einst Thel'tha, der Schöpfer Angrothea’s selbst, gelebt hatte. Sie wussten, dass dort die Quelle des Lebens und somit auch die Seele von Angrothea verborgen war.", 32F, true);
                if (gamePanel.keyHandler.enterPressed) {
                    scenePhase++;
                    gamePanel.keyHandler.enterPressed = false;
                }
            } else gamePanel.keyHandler.enterPressed = false;

        }

        if (scenePhase == 8) {

            fillScreenBlack(1F);

            double ratio = (double) gamePanel.screenHeight / scene0[3].getHeight();
            int width = (int) (scene0[3].getWidth() * ratio);
            int height = (int) (scene0[3].getHeight() * ratio);

            g2.drawImage(scene0[3], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha += 0.02f;
            if (alpha > 1f) {
                alpha = 1f;
            }

            fillScreenBlack(alpha);

            if (alpha == 1f) {
                scenePhase++;
            }

        }

        if (scenePhase == 9) {

            double ratio = (double) gamePanel.screenHeight / scene0[4].getHeight();
            int width = (int) (scene0[4].getWidth() * ratio);
            int height = (int) (scene0[4].getHeight() * ratio);

            g2.drawImage(scene0[4], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha -= 0.02f;
            if (alpha < 0f) {
                alpha = 0f;
            }

            fillScreenBlack(alpha);

            if (alpha == 0f) {
                ui.drawSimpleDialog(0, -1, "Sie betraten die Festung, nutzten die reine Energie, die ihnen dort zur Verfügung stand, und bildeten ein Gefängnis für den Dämon.", 32F, true);
                if (gamePanel.keyHandler.enterPressed) {
                    scenePhase++;
                    gamePanel.keyHandler.enterPressed = false;
                }
            } else gamePanel.keyHandler.enterPressed = false;

        }

        if (scenePhase == 10) {

            fillScreenBlack(1F);

            double ratio = (double) gamePanel.screenHeight / scene0[4].getHeight();
            int width = (int) (scene0[4].getWidth() * ratio);
            int height = (int) (scene0[4].getHeight() * ratio);

            g2.drawImage(scene0[4], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha += 0.02f;
            if (alpha > 1f) {
                alpha = 1f;
            }

            fillScreenBlack(alpha);

            if (alpha == 1f) {
                scenePhase++;
            }

        }

        if (scenePhase == 11) {

            double ratio = (double) gamePanel.screenHeight / scene0[5].getHeight();
            int width = (int) (scene0[5].getWidth() * ratio);
            int height = (int) (scene0[5].getHeight() * ratio);

            g2.drawImage(scene0[5], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha -= 0.02f;
            if (alpha < 0f) {
                alpha = 0f;
            }

            fillScreenBlack(alpha);

            if (alpha == 0f) {
                ui.drawSimpleDialog(0, -1, "Und als das Licht immer heller wurde und die Gestalt schließlich selbst auf Angrothea erschien, packten die Wesen sie und sperrten sie in der Feste Thaural ein.", 32F, true);
                if (gamePanel.keyHandler.enterPressed) {
                    scenePhase++;
                    gamePanel.keyHandler.enterPressed = false;
                }
            } else gamePanel.keyHandler.enterPressed = false;

        }

        if (scenePhase == 12) {

            fillScreenBlack(1F);

            double ratio = (double) gamePanel.screenHeight / scene0[5].getHeight();
            int width = (int) (scene0[5].getWidth() * ratio);
            int height = (int) (scene0[5].getHeight() * ratio);

            g2.drawImage(scene0[5], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha += 0.02f;
            if (alpha > 1f) {
                alpha = 1f;
            }

            fillScreenBlack(alpha);

            if (alpha == 1f) {
                scenePhase++;
            }

        }

        if (scenePhase == 13) {

            double ratio = (double) gamePanel.screenHeight / scene0[6].getHeight();
            int width = (int) (scene0[6].getWidth() * ratio);
            int height = (int) (scene0[6].getHeight() * ratio);

            g2.drawImage(scene0[6], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha -= 0.02f;
            if (alpha < 0f) {
                alpha = 0f;
            }

            fillScreenBlack(alpha);

            if (alpha == 0f) {
                ui.drawSimpleDialog(0, -1, "Sie waren allerdings nicht kräftig genug, um das Geschöpf für immer zu verbannen. Und seitdem her bröckeln die Wände des magischen Gefängnisses.", 32F, true);
                if (gamePanel.keyHandler.enterPressed) {
                    scenePhase++;
                    gamePanel.keyHandler.enterPressed = false;
                }
            } else gamePanel.keyHandler.enterPressed = false;

        }

        if (scenePhase == 14) {

            fillScreenBlack(1F);

            double ratio = (double) gamePanel.screenHeight / scene0[6].getHeight();
            int width = (int) (scene0[6].getWidth() * ratio);
            int height = (int) (scene0[6].getHeight() * ratio);

            g2.drawImage(scene0[6], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha += 0.02f;
            if (alpha > 1f) {
                alpha = 1f;
            }

            fillScreenBlack(alpha);

            if (alpha == 1f) {
                scenePhase++;
            }

        }

        if (scenePhase == 15) {

            double ratio = (double) gamePanel.screenHeight / scene0[7].getHeight();
            int width = (int) (scene0[7].getWidth() * ratio);
            int height = (int) (scene0[7].getHeight() * ratio);

            g2.drawImage(scene0[7], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha -= 0.02f;
            if (alpha < 0f) {
                alpha = 0f;
            }

            fillScreenBlack(alpha);

            if (alpha == 0f) {
                ui.drawSimpleDialog(0, -1, "Als sie die Festung verließen, sprach jeder von ihnen einen Zauberspruch, um das Tor für immer zu versiegeln. Nur wenn alle Formeln noch einmal vor dem Eingang der Feste Thaural ausgesprochen werden würden, sollte sich die Forte noch einmal öffnen lassen.", 32F, true);
                if (gamePanel.keyHandler.enterPressed) {
                    scenePhase++;
                    gamePanel.keyHandler.enterPressed = false;
                }
            } else gamePanel.keyHandler.enterPressed = false;

        }

        if (scenePhase == 16) {

            fillScreenBlack(1F);

            double ratio = (double) gamePanel.screenHeight / scene0[7].getHeight();
            int width = (int) (scene0[7].getWidth() * ratio);
            int height = (int) (scene0[7].getHeight() * ratio);

            g2.drawImage(scene0[7], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha += 0.02f;
            if (alpha > 1f) {
                alpha = 1f;
            }

            fillScreenBlack(alpha);

            if (alpha == 1f) {
                scenePhase++;
            }

        }

        if (scenePhase == 17) {

            gamePanel.keyHandler.enterPressed = false;

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));
            g2.drawString("Jetzt (etwa 450 Jahre später)", RenderUtils.getXForCenteredText("Jetzt (etwa 450 Jahre später)", g2, gamePanel), gamePanel.screenHeight / 2);

            alpha -= 0.02f;
            if (alpha < 0f) {
                alpha = 0f;
            }

            fillScreenBlack(alpha);

            if (alpha == 0f) {
                if (gamePanel.scheduleMusic(SoundPlayer.MUSIC_DEMON_BATTLE_OUTRO, false)) {
                    scenePhase++;
                }
            } else gamePanel.keyHandler.enterPressed = false;

        }

        if (scenePhase == 18) {

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 64F));
            g2.drawString("Jetzt (etwa 450 Jahre später)", RenderUtils.getXForCenteredText("Jetzt (etwa 450 Jahre später)", g2, gamePanel), gamePanel.screenHeight / 2);

            alpha += 0.02f;
            if (alpha > 1f) {
                alpha = 1f;
            }

            fillScreenBlack(alpha);

            if (alpha == 1f) {
                scenePhase++;
                gamePanel.playMusic(SoundPlayer.MUSIC_DEMON_BATTLE_SMALL, false);
            }

        }

        if (scenePhase == 19) {

            double ratio = (double) gamePanel.screenHeight / scene0[8].getHeight();
            int width = (int) (scene0[8].getWidth() * ratio);
            int height = (int) (scene0[8].getHeight() * ratio);

            g2.drawImage(scene0[8], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha -= 0.02f;
            if (alpha < 0f) {
                alpha = 0f;
            }

            fillScreenBlack(alpha);

            if (alpha == 0f) {
                ui.drawSimpleDialog(0, -1, "Du stehst vor dem komplett verwüsteten Haus deiner Eltern und die Trauer steigt dir als Tränen in die Augen. Leute hatten dir erzählt was geschehen war. Sie hatten von einem risiegen Feuerball gesprochen und manche behaupteten eine vermummte Gestallt gesehen zu haben, die vor dem Haus etwas gemurmelt hatte. Du weißt nicht was du davon halten sollst und es ist dir auch egal. Plötzlich legt dir jemand seine Hand auf die Schulter. Als du dich umdrehst siehst du einen alten Mann mit ungewöhnlich langem Bart und Haar.", 32F, true);
                if (gamePanel.keyHandler.enterPressed) {
                    scenePhase++;
                    gamePanel.keyHandler.enterPressed = false;
                }
            } else gamePanel.keyHandler.enterPressed = false;

        }

        if (scenePhase == 20) {

            double ratio = (double) gamePanel.screenHeight / scene0[8].getHeight();
            int width = (int) (scene0[8].getWidth() * ratio);
            int height = (int) (scene0[8].getHeight() * ratio);

            g2.drawImage(scene0[8], ui.getXForCenteredSomething(width), 0, width, height, null);

            alpha += 0.02f;
            if (alpha > 1f) {
                alpha = 1f;
            }

            fillScreenBlack(alpha);

            if (alpha == 1f) {
                scenePhase++;
            }

        }

        if (scenePhase == 21) {

            gamePanel.gameState = GameState.PLAY;

        }

    }

    public void fillScreenBlack(float alpha) {

        g2.setColor(Color.BLACK);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

    }

    public boolean timerReached(int target) {

        timer++;

        if (timer == target) {
            timer = 0;
            return true;
        } else return false;

    }

}
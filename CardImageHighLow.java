package project; //仮で「project」と記載
import javax.swing.*; //Swingライブラリのすべてのクラスをインポート
import java.awt.*; //AWTのすべてのクラスをインポート,レイアウトや描画に関するクラス
import java.awt.event.*; //AWTのイベント関連クラスをすべてインポート,ユーザーの操作用のイベントクラス等
import java.util.*; //Javaのユーティリティクラスをすべてインポート
import java.util.List; //java.utilのListだけをインポート


public class CardImageHighLow extends JFrame { //JFrameを継承することでウィンドウアプリとして動作
    private JLabel cardLabel, resultLabel, scoreLabel, remainingLabel; //カード画像や結果、スコア、残りカード数を表示
    private JButton highButton, lowButton, resetButton; //「High」「Low」「もう一回」ボタン
    private Card currentCard, nextCard; //現在のカードと次のカードを保持
    private Deck deck; //山札
    private int score = 0; //スコアカウンター

    public CardImageHighLow() { //ウィンドウのサイズやタイトルを設定
        setTitle("ハイローゲーム"); //ウィンドウのタイトルバーに「ハイローゲーム」に設定
        setSize(450, 500); //ウィンドウの横幅を450ピクセル、高さを500ピクセルに設定
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ウィンドウの×ボタンでアプリケを終了
        setLocationRelativeTo(null); //ウィンドウを画面の中央に配置

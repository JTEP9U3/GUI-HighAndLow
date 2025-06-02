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

        deck = new Deck(); //新しい山札を作成

        cardLabel = new JLabel(); //GUIのためにラベルを作成
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER); //画像やテキストを水平方向,中央に配置する設定
    
        resultLabel = new JLabel("High または Low を選んでください", SwingConstants.CENTER); //GUIコンポーネントを作成して「High または Low を選んでください」を表示
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); //ラベルのフォント設定

        scoreLabel = new JLabel("スコア: 0", SwingConstants.CENTER); //スコア表示
        remainingLabel = new JLabel("残りカード: 52", SwingConstants.CENTER); //残りカード数表示

        highButton = new JButton("High"); //ボタン作成「High」
        lowButton = new JButton("Low"); //ボタン作成「Low」
        resetButton = new JButton("もう一回"); //ボタン作成「もう一回」
        resetButton.setEnabled(false); //ボタンの初期状態を無効化（クリックできない状態）に設定する

        highButton.addActionListener(e -> playRound(true)); //「High」ボタンに対してアクションを設定:「高い値を選択」
        lowButton.addActionListener(e -> playRound(false)); //「Low」ボタンに対してアクションを設定:「低い値を選択」
        resetButton.addActionListener(e -> resetGame()); //「リセット」ボタンに対してアクションを設定:「リセット処理」

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

        JPanel buttonPanel = new JPanel(); //パネルの新規作成,ボタン用
        buttonPanel.add(highButton); //ボタン設置「High」
        buttonPanel.add(lowButton); //ボタン設置「Low」
        buttonPanel.add(resetButton); //ボタン設置「もう一回」
        
        JPanel topPanel = new JPanel(new GridLayout(3, 1)); //パネルの新規作成,3行1列のグリッドレイアウトを設定
        topPanel.add(resultLabel); //ラベル追加「resultLabel」
        topPanel.add(scoreLabel); //ラベル追加「scoreLabel」
        topPanel.add(remainingLabel); //ラベル追加「remainingLabel」
        
        setLayout(new BorderLayout()); //上下左右と中央にコンポーネントを配置できるレイアウト設定
        add(topPanel, BorderLayout.NORTH); //レイアウトの上部に配置
        add(cardLabel, BorderLayout.CENTER); //レイアウトの中央に配置
        add(buttonPanel, BorderLayout.SOUTH); //レイアウトの下部に配置
        
        startGame(); //ゲームのスタート処理
    }
    
    private void startGame() { //スコア初期化、デッキをシャッフル、1枚ドローして表示
        score = 0; //スコアを初期化
        deck.shuffle(); //山札をシャッフル
        currentCard = deck.drawCard(); //最初のカードを1枚引く
        showCard(currentCard); //画像を表示
        resultLabel.setText("High または Low を選んでください");
        updateScore(); //スコアを初期化
        updateRemaining(); //残りカード数表示
    }
    
    private void playRound(boolean guessHigh) { //1ラウンドのプレイ
        nextCard = deck.drawCard(); //山札からカードを引く
        if (nextCard == null) { //カードが引けなかった場合
            resultLabel.setText("デッキが空です。"); //山札がないことを表示
            return; //処理を終了
        }
        
        showCard(nextCard); //カードの開示
        updateRemaining(); //山札などのカード情報を更新
        
        boolean isWin = (guessHigh && nextCard.getValue() > currentCard.getValue()) ||
                        (!guessHigh && nextCard.getValue() < currentCard.getValue());
        //値が同じ → 引き分け（スコア0）・勝ち → スコア +1、currentCard を更新・負け → スコア0、ゲームオーバー
        
        if (nextCard.getValue() == currentCard.getValue()) {
            resultLabel.setText("引き分け！");
            score = 0; //次のカードと現在のカードの値が一致した場合,「引き分け」と表示しスコアをリセット
        } else if (isWin) {
            resultLabel.setText("正解！");
            score++;
            currentCard = nextCard; //予想が当たれば「正解」と表示し,スコアを増やし,次のカードを現在のカードとして更新
        } else {
            resultLabel.setText("不正解！ゲームオーバー！");
            score = 0; //予想が外れたら「不正解」と表示し、スコアをリセット
        }
        
        updateScore(); //スコアの更新
        
        if (!isWin || deck.remaining() == 0) {
            highButton.setEnabled(false);
            lowButton.setEnabled(false);
            resetButton.setEnabled(true);
            //ゲームが終了した（敗北,山札0枚）場合にリセットボタンのみを押せる状態にする
        }
    }
    
    private void resetGame() {
        startGame();
        highButton.setEnabled(true);
        lowButton.setEnabled(true);
        resetButton.setEnabled(false);
        //ゲームに再挑戦するための処理
    }
    
    private void updateScore() {
        scoreLabel.setText("スコア: " + score); //画面上にスコアを更新
    }
    
    private void updateRemaining() {
        remainingLabel.setText("残りカード: " + deck.remaining()); //山札に残っているカードの数を表示
    }
    
    private void showCard(Card card) { //カードの画像表示処理
        String path = "cards/" + card.getImageFileName(); //カードの画像ファイルのパス
        ImageIcon icon = new ImageIcon(path); //画像ファイルの読み込み
        Image scaled = icon.getImage().getScaledInstance(150, 220, Image.SCALE_SMOOTH); //画像を幅150ピクセル、高さ220ピクセルにリサイズ
        cardLabel.setIcon(new ImageIcon(scaled)); //リサイズされたカードの画像を表示
    }
    
    public static void main(String[] args) { //エントリーポイント
        SwingUtilities.invokeLater(() -> new CardImageHighLow().setVisible(true)); //GUIクラスのインスタンスを作成とウィンドウへの表示
    }
    
    // ===== 内部クラス: Card =====
    private class Card { //定義されているクラスの内部からのみアクセス可
        private String suit; //カードのスート
        private int value; //カードの数値
        
        public Card(String suit, int value) {
            this.suit = suit;
            this.value = value;
        }
        
        public int getValue() { //外部クラスから数値を取得
            return value;
        }
        
        public String getImageFileName() { //一部の数値をトランプ用に変換する処理
            String valueStr;
            switch (value) {
                case 1: valueStr = "A"; break;
                case 11: valueStr = "J"; break;
                case 12: valueStr = "Q"; break;
                case 13: valueStr = "K"; break;
                default: valueStr = String.valueOf(value);
            }
            
            String suitLetter = switch (suit) { //スートの扱いを簡略的にする処理
                case "Spades" -> "S";
                case "Hearts" -> "H";
                case "Diamonds" -> "D";
                case "Clubs" -> "C";
                default -> "?";
            };
            
            return valueStr + suitLetter + ".png"; //画像ファイルへのパスを生成
        }
        
        public String toString() { //オブジェクトを文字列として表現するための処理
            return value + " of " + suit;
        }
    }
    
    // ===== 内部クラス: Deck =====
    
    private class Deck { //山札の管理
        private List<Card> cards = new ArrayList<>();
        private Random rand = new Random();
        
        public Deck() { //52枚のカードを生成し,各スートに対して1から13までのカードを作成,それを山札に追加
            String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
            for (String suit : suits) {
                for (int i = 1; i <= 13; i++) {
                    cards.add(new Card(suit, i));
                }
            }
        }
        
        public void shuffle() { //山札のシャッフル
            Collections.shuffle(cards, rand);
        }
        
        public Card drawCard() { //山札からカードを引く
            if (cards.isEmpty()) return null;
            return cards.remove(0);
        }
        
        public int remaining() { //カード残り枚数などの情報を取得
            return cards.size();
        }
    }
}

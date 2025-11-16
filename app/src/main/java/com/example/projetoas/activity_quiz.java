package com.example.projetoas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;


public class activity_quiz extends AppCompatActivity {


    CardView cardCiencias, cardPortugues, cardArte, cardIngles,
            cardMatematica, cardHistoria, cardGeografia, cardCultura,
            cardAllSubjects;


    View subjectSelectionArea;


    View quizContainer;
    TextView tvSubjectTitle, tvQuestion, tvScore;
    RadioGroup rgOptions;
    RadioButton rbOpt1, rbOpt2, rbOpt3, rbOpt4;
    Button btnNext, btnFinish;

    ImageButton btnBackFromQuiz;

    String currentSubject = "";
    int currentQuestionIndex = 0;
    int score = 0;




    List<Question> currentQuestionsList;




    Map<String, List<Question>> questionBank = new HashMap<>();








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo_quiz);




        cardCiencias = findViewById(R.id.card_ciencias);
        cardPortugues = findViewById(R.id.card_portugues);
        cardArte = findViewById(R.id.card_arte);
        cardIngles = findViewById(R.id.card_ingles);
        cardMatematica = findViewById(R.id.card_matematica);
        cardHistoria = findViewById(R.id.card_historia);
        cardGeografia = findViewById(R.id.card_geografia);
        cardAllSubjects = findViewById(R.id.card_all_subjects);




        subjectSelectionArea = findViewById(R.id.subject_selection_area);
        quizContainer = findViewById(R.id.quiz_container);




        tvSubjectTitle = findViewById(R.id.tv_subject_title);
        tvQuestion = findViewById(R.id.tv_question);
        tvScore = findViewById(R.id.tv_score);
        rgOptions = findViewById(R.id.rg_options);
        rbOpt1 = findViewById(R.id.rb_opt1);
        rbOpt2 = findViewById(R.id.rb_opt2);
        rbOpt3 = findViewById(R.id.rb_opt3);
        rbOpt4 = findViewById(R.id.rb_opt4);
        btnNext = findViewById(R.id.btn_next);
        btnFinish = findViewById(R.id.btn_finish);




        btnBackFromQuiz = findViewById(R.id.btn_back_from_quiz);




        setupQuestions();




        setSubjectClick(cardCiencias, "Ciências");
        setSubjectClick(cardPortugues, "Português");
        setSubjectClick(cardArte, "Arte");
        setSubjectClick(cardIngles, "Inglês");
        setSubjectClick(cardMatematica, "Matemática");
        setSubjectClick(cardHistoria, "História");
        setSubjectClick(cardGeografia, "Geografia");
        setSubjectClick(cardAllSubjects, "Todas");








        btnNext.setOnClickListener(v -> checkAnswer());








        btnFinish.setOnClickListener(v -> finishQuiz());








        btnBackFromQuiz.setOnClickListener(v -> handleBackAction());
    }








    private void handleBackAction() {
        if (quizContainer.getVisibility() == View.VISIBLE) {
            finishQuiz();
        } else {
            super.onBackPressed();
        }
    }




    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        if (quizContainer.getVisibility() == View.VISIBLE) {
            finishQuiz();
        } else {
            super.onBackPressed();
        }
    }
















    private void setSubjectClick(CardView card, String subject) {
        card.setOnClickListener(v -> startQuiz(subject));
    }








    private void startQuiz(String subject) {
        currentSubject = subject;
        currentQuestionIndex = 0;
        score = 0;
        tvScore.setText("Pontuação: 0");








        // Inicializa a lista de perguntas para a rodada atual.
        currentQuestionsList = new ArrayList<>();








        // Lógica para o modo "Todas as Matérias"
        if (subject.equals("Todas")) {
            tvSubjectTitle.setText("Matéria: Todas as Matérias");








            // Cria a lista combinada de todas as matérias exceto "Todas"
            List<Question> allQuestions = new ArrayList<>();
            for (Map.Entry<String, List<Question>> entry : questionBank.entrySet()) {
                // Apenas adiciona se não for a chave "Todas"
                if (!entry.getKey().equals("Todas")) {
                    allQuestions.addAll(entry.getValue());
                }
            }








            // Embaralha as perguntas
            Collections.shuffle(allQuestions);








            // Atribui a lista embaralhada como a lista de perguntas atual da rodada.
            currentQuestionsList = allQuestions;








        } else {
            // Para quizzes de matérias únicas, busca a lista do questionBank
            tvSubjectTitle.setText("Matéria: " + subject);








            // VERIFICAÇÃO DE SEGURANÇA: Garante que a lista existe.
            List<Question> subjectQuestions = questionBank.get(subject);
            if (subjectQuestions != null) {
                // OPÇÃO: Embaralhe a matéria única também para variar a ordem.
                List<Question> shuffledList = new ArrayList<>(subjectQuestions);
                Collections.shuffle(shuffledList);
                currentQuestionsList = shuffledList;
            } else {
                // Lidar com o caso de matéria inexistente (embora improvável)
                Toast.makeText(this, "Matéria não encontrada!", Toast.LENGTH_SHORT).show();
                return;
            }
        }








        // Esconde a área de seleção e mostra o quiz
        subjectSelectionArea.setVisibility(View.GONE);
        quizContainer.setVisibility(View.VISIBLE);








        showQuestion();
    }








    // MÉTODO setupQuestions - PERMANECE SEM ALTERAÇÕES
    private void setupQuestions() {
        // === Ciências 🔬 ===
        List<Question> ciencias = new ArrayList<>();
        ciencias.add(new Question("A água ferve a quantos graus Celsius?",
                new String[]{"50°C", "100°C", "150°C", "200°C"}, 1));
        ciencias.add(new Question("Qual planeta é conhecido como planeta vermelho?",
                new String[]{"Terra", "Marte", "Júpiter", "Vênus"}, 1));
        ciencias.add(new Question("Qual gás as plantas absorvem para a fotossíntese?",
                new String[]{"Oxigênio", "Nitrogênio", "Gás Carbônico", "Hélio"}, 2));
        ciencias.add(new Question("Qual é o maior órgão do corpo humano?",
                new String[]{"Coração", "Fígado", "Pele", "Cérebro"}, 2));
        ciencias.add(new Question("Qual é a unidade básica da vida?",
                new String[]{"Molécula", "Átomo", "Célula", "Tecido"}, 2));
        ciencias.add(new Question("O que é um eclipse lunar?",
                new String[]{"A Lua passa na frente do Sol", "A Terra passa na frente da Lua", "A Lua passa na sombra da Terra", "O Sol se apaga"}, 2));
















        // === Português 🇧🇷 ===
        List<Question> portugues = new ArrayList<>();
        portugues.add(new Question("Qual é o plural de 'cidadão'?",
                new String[]{"Cidadãos", "Cidadães", "Cidadões", "Cidadãns"}, 2));
        portugues.add(new Question("O verbo 'cantar' está em qual conjugação?",
                new String[]{"1ª", "2ª", "3ª", "Nenhuma"}, 0));
        portugues.add(new Question("Qual é o antônimo de 'benevolente'?",
                new String[]{"Bondoso", "Caridoso", "Maligno", "Gentil"}, 2));
        portugues.add(new Question("Em qual tempo verbal está a frase: 'Eu comerei o bolo.'",
                new String[]{"Presente", "Passado", "Futuro do Presente", "Infinitivo"}, 2));
        portugues.add(new Question("Qual figura de linguagem é 'O sol beijava a pele dela'?",
                new String[]{"Metáfora", "Hipérbole", "Personificação", "Ironia"}, 2));
















        // === Matemática ➕ ===
        List<Question> matematica = new ArrayList<>();
        matematica.add(new Question("Quanto é 7 x 8?", new String[]{"54", "56", "64", "58"}, 1));
        matematica.add(new Question("A fração 1/2 é igual a quantos por cento?",
                new String[]{"25%", "50%", "75%", "100%"}, 1));
        matematica.add(new Question("Qual é o valor de π (Pi) aproximado?",
                new String[]{"3,10", "3,14", "3,20", "3,00"}, 1));
        matematica.add(new Question("Qual é o resultado de 15 + (5 * 2)?",
                new String[]{"40", "25", "30", "20"}, 1)); // 15 + 10 = 25. O índice deve ser 1. CORRIGIDO
        matematica.add(new Question("Um triângulo com todos os lados iguais é chamado de:",
                new String[]{"Isósceles", "Escaleno", "Retângulo", "Equilátero"}, 3));
















        // === História 📜 ===
        List<Question> historia = new ArrayList<>();
        historia.add(new Question("Quem foi o primeiro presidente do Brasil?",
                new String[]{"Getúlio Vargas", "Deodoro da Fonseca", "Dom Pedro II", "JK"}, 1));
        historia.add(new Question("Em que ano o Brasil declarou sua independência?",
                new String[]{"1808", "1822", "1500", "1900"}, 1));
        historia.add(new Question("O que foi a 'Guerra Fria'?",
                new String[]{"Um conflito nuclear", "Uma disputa ideológica entre EUA e URSS", "Uma guerra na Antártida", "Uma disputa comercial"}, 1));
        historia.add(new Question("Quem liderou a Revolução Russa de 1917?",
                new String[]{"Josef Stalin", "Leon Trotsky", "Vladimir Lenin", "Mikhail Gorbachev"}, 2));
















        // === Arte 🎨 ===
        List<Question> arte = new ArrayList<>();
        arte.add(new Question("Quem pintou a 'Mona Lisa'?",
                new String[]{"Van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"}, 2));
        arte.add(new Question("Qual movimento artístico utiliza figuras geométricas e cores primárias de forma abstrata?",
                new String[]{"Impressionismo", "Cubismo", "Surrealismo", "Abstracionismo Lírico"}, 1));
        arte.add(new Question("Qual artista é famoso por ter 'cortado a própria orelha'?",
                new String[]{"Salvador Dalí", "Vincent van Gogh", "Paul Cézanne", "Edgar Degas"}, 1));
















        // === Inglês 🇺🇸 ===
        List<Question> ingles = new ArrayList<>();
        ingles.add(new Question("Qual a tradução de 'Apple'?",
                new String[]{"Laranja", "Banana", "Maçã", "Pêra"}, 2));
        ingles.add(new Question("Qual é o passado simples de 'to go'?",
                new String[]{"Goes", "Gone", "Went", "Going"}, 2));
        ingles.add(new Question("Como se diz 'cachorro' em inglês?",
                new String[]{"Cat", "Bird", "Dog", "Fish"}, 2));
















        // === Geografia 🌍 ===
        List<Question> geografia = new ArrayList<>();
        geografia.add(new Question("Qual é o maior oceano do mundo?",
                new String[]{"Atlântico", "Índico", "Pacífico", "Ártico"}, 2));
        geografia.add(new Question("Qual continente o Brasil está localizado?",
                new String[]{"Europa", "África", "Ásia", "América do Sul"}, 3));
        geografia.add(new Question("Qual é a capital do Canadá?",
                new String[]{"Toronto", "Vancouver", "Montreal", "Ottawa"}, 3));
        geografia.add(new Question("O deserto do Saara está localizado em qual continente?",
                new String[]{"Ásia", "América do Norte", "África", "Austrália"}, 2));
















        // === Cultura 🎭 ===
        List<Question> cultura = new ArrayList<>();
        cultura.add(new Question("Qual festa popular brasileira é famosa por marchinhas, frevo e samba?",
                new String[]{"Festa Junina", "Natal", "Carnaval", "Páscoa"}, 2));
        cultura.add(new Question("Onde está localizada a estátua do Cristo Redentor?",
                new String[]{"São Paulo", "Salvador", "Rio de Janeiro", "Belo Horizonte"}, 2));
        cultura.add(new Question("Qual instrumento musical é típico do samba e choro?",
                new String[]{"Violino", "Trompete", "Cavaquinho", "Piano"}, 2));








        // Adiciona todas as listas de perguntas ao questionBank
        questionBank.put("Ciências", ciencias);
        questionBank.put("Português", portugues);
        questionBank.put("Matemática", matematica);
        questionBank.put("História", historia);
        questionBank.put("Arte", arte);
        questionBank.put("Inglês", ingles);
        questionBank.put("Geografia", geografia);
        questionBank.put("Cultura", cultura);
    }








    private void showQuestion() {
        // Usa a lista de perguntas da rodada atual (currentQuestionsList)
        List<Question> questions = currentQuestionsList;








        // VERIFICAÇÃO DE SEGURANÇA: currentQuestionsList NUNCA DEVERIA SER NULA/VAZIA, mas é bom verificar.
        if (questions == null || questions.isEmpty()) {
            tvQuestion.setText("Erro: Sem perguntas disponíveis.");
            btnNext.setEnabled(false);
            return;
        }








        btnNext.setEnabled(true);








        if (currentQuestionIndex >= questions.size()) {
            // Se as perguntas acabaram, finalize o quiz
            finishQuiz();
            return;
        }








        Question q = questions.get(currentQuestionIndex);
        tvQuestion.setText(q.getQuestionText());
        rbOpt1.setText(q.getOptions()[0]);
        rbOpt2.setText(q.getOptions()[1]);
        rbOpt3.setText(q.getOptions()[2]);
        rbOpt4.setText(q.getOptions()[3]);
        rgOptions.clearCheck();
    }








    private void checkAnswer() {
        int selectedId = rgOptions.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Selecione uma resposta!", Toast.LENGTH_SHORT).show();
            return;
        }








        RadioButton selectedButton = findViewById(selectedId);








        // Usa a lista de perguntas da rodada atual
        Question currentQ = currentQuestionsList.get(currentQuestionIndex);








        // Determina o índice da resposta selecionada
        int selectedIndex = -1;
        // MELHORIA: Usa switch ou a propriedade ID do RadioButton para maior robustez
        if (selectedButton == rbOpt1) selectedIndex = 0;
        else if (selectedButton == rbOpt2) selectedIndex = 1;
        else if (selectedButton == rbOpt3) selectedIndex = 2;
        else if (selectedButton == rbOpt4) selectedIndex = 3;








        if (selectedIndex == currentQ.getCorrectIndex()) {
            score++;




        } else {
            String correctOption = currentQ.getOptions()[currentQ.getCorrectIndex()];
            Toast.makeText(this, "Errado! A resposta correta era: " + correctOption, Toast.LENGTH_SHORT).show(); // Usando LENGTH_SHORT
        }








        tvScore.setText("Pontuação: " + score);
        currentQuestionIndex++;
        showQuestion();
    }








    private void finishQuiz() {
        String subjectName = currentSubject.equals("Todas") ? "Todas as Matérias" : currentSubject;
        // Usa currentQuestionsList para obter o total de perguntas
        int totalQuestions = currentQuestionsList != null ? currentQuestionsList.size() : 0;








        // Apenas mostra o resultado se o usuário realmente estava em um quiz (não apenas clicou em "sair" na tela de seleção)
        if (quizContainer.getVisibility() == View.VISIBLE && totalQuestions > 0) {
            Toast.makeText(this, "Fim do quiz de " + subjectName + "! Pontuação final: " + score + " de " + totalQuestions, Toast.LENGTH_LONG).show();
        }
















        // Esconde o quiz e mostra a área de seleção
        quizContainer.setVisibility(View.GONE);
        subjectSelectionArea.setVisibility(View.VISIBLE);








        // Reinicia variáveis de controle
        currentQuestionIndex = 0;
        score = 0;
        currentSubject = "";
        currentQuestionsList = null; // Libera a lista da rodada anterior
    }








    // Classe interna para representar uma pergunta - PERMANECE SEM ALTERAÇÕES
    static class Question {
        private final String questionText;
        private final String[] options;
        private final int correctIndex;








        public Question(String questionText, String[] options, int correctIndex) {
            this.questionText = questionText;
            this.options = options;
            this.correctIndex = correctIndex;
        }








        public String getQuestionText() { return questionText; }
        public String[] getOptions() { return options; }
        public int getCorrectIndex() { return correctIndex; }
    }
}
package com.meuprojeto;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.core.credential.AzureKeyCredential;

public class AnalisadorDeSentimento {

    private static final String CHAVE = "EQhx0bfaCLUgbM3ESWmL4DTI07QJwpoWQjkZhCc050b1EsGmAP24JQQJ99BJACrIdLPXJ3w3AAAaACOGkp0s";
    private static final String PONTO_DE_EXTREMIDADE = "https://pacote-ia-marcus-1921.cognitiveservices.azure.com/";

    public static void main(String[] args) {
        System.out.println("Iniciando a conexão com o Serviço Cognitivo do Azure...");

        TextAnalyticsClient client = new TextAnalyticsClientBuilder()
                .credential(new AzureKeyCredential(CHAVE))
                .endpoint(PONTO_DE_EXTREMIDADE)
                .buildClient();

        System.out.println("Conexão estabelecida com sucesso!");

        String frasePositiva = "Que curso fantástico! Aprendi muito e o professor é excelente.";
        String fraseNegativa = "Estou muito decepcionado com a demora na entrega do produto.";
        String fraseNeutra = "A reunião está agendada para amanhã às 10h.";

        analisarSentimento(client, frasePositiva);
        analisarSentimento(client, fraseNegativa);
        analisarSentimento(client, fraseNeutra);
    }

    private static void analisarSentimento(TextAnalyticsClient client, String texto) {
        try {
            DocumentSentiment documentSentiment = client.analyzeSentiment(texto, "pt-BR");

            System.out.println("----------------------------------------------------");
            System.out.printf("Frase analisada: \"%s\"%n", texto);
            System.out.printf("    -> Sentimento Geral Detectado: %s%n", documentSentiment.getSentiment());

            System.out.printf("    -> Pontuação de Confiança: Positivo=%.2f, Neutro=%.2f, Negativo=%.2f%n",
                    documentSentiment.getConfidenceScores().getPositive(),
                    documentSentiment.getConfidenceScores().getNeutral(),
                    documentSentiment.getConfidenceScores().getNegative());

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao analisar a frase: " + e.getMessage());
        }
    }
}
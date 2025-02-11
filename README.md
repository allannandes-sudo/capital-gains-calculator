# 📈 Capital Gains Calculator

Este projeto implementa um **calculador de ganho de capital** para operações de compra e venda de ativos.
Ele processa uma lista de operações e calcula os impostos devidos com base nas regras estabelecidas.
Esta aplicação pode ser executada tanto rodando o arquivo JAR diretamente quanto usando a ferramenta de build Gradle.
## 🛠️ Tecnologias Utilizadas

- **Java 21** (ou superior)
- **Gradle** para gerenciamento de dependências
- **Jackson** para manipulação de JSON
- **JUnit 5** para testes unitários
- **Lombok** para reduzir código boilerplate


# Executando a Aplicação
Usando o Arquivo JAR
1. Construa o projeto para gerar o arquivo JAR.
2. Navegue até o diretório do projeto.
3. Execute o seguinte comando no terminal:

Windows:
    
- java -jar build/libs/capital-gains-calculator-1.0-SNAPSHOT-all.jar input.json

Linux/Mac:
- java -jar build/libs/capital-gains-calculator-1.0-SNAPSHOT-all.jar input.json

Para passar um caminho de arquivo em vez de um nome de arquivo:
- java -jar build/libs/capital-gains-calculator-1.0-SNAPSHOT-all.jar <CAMINHO>



✅ Rodar com Gradle

Se o Gradle estiver configurado corretamente, o comando é:
- ./gradlew run --args="input.json"

Se estiver no Windows e o Gradle não estiver instalado globalmente:
- gradlew.bat run --args="input.json"


O --args="input.json" arquivo tem que esta na raiz do projeto ou --args="<PATH>"


# Depois, insira os dados no formato JSON e finalize com:

- Windows: Ctrl+Z + Enter
- Linux/macOS: Ctrl+D

📂 Estrutura do Projeto

capital-gains-calculator/
├── src/main/java/com/capital/capital_gains_calculator/
│   ├── Main.java                    # Ponto de entrada da aplicação
│   ├── dto/                          # Classes DTO para operações e impostos
│   ├── service/                       # Interface para cálculo de ganho de capital
│   ├── service/impl/                   # Implementação do cálculo
├── src/test/java/com/capital/         # Testes unitários
├── build.gradle                       # Configuração do Gradle
└── README.md                           # Documentação do projeto

📊 Exemplo de Entrada e Saída

[
{"operation": "buy", "unit-cost": 10.00, "quantity": 100},
{"operation": "sell", "unit-cost": 15.00, "quantity": 50},
{"operation": "sell", "unit-cost": 15.00, "quantity": 50}
]

[
{"operation": "buy", "unit-cost": 10.00, "quantity": 10000},
{"operation": "sell", "unit-cost": 20.00, "quantity": 5000},
{"operation": "sell", "unit-cost": 5.00, "quantity": 5000}
]

📤 Saída Esperada

[{"tax": 0.00}, {"tax": 0.00}, {"tax": 0.00}]
[{"tax": 0.00}, {"tax": 10000.00}, {"tax": 0.00}]




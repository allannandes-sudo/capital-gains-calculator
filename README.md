# Passos 
✅ Rodar o JAR diretamente
Se já gerou um JAR com dependências (fat JAR) usando ./gradlew clean shadowJar, use:

# Powershell
Passnado um arquivo JSON na raiz do projeto, use:

- java -jar build/libs/capital-gains-calculator-1.0-SNAPSHOT-all.jar input.json 

Passnado um arquivo JSON <PATH>, use:

- java -jar build/libs/capital-gains-calculator-1.0-SNAPSHOT-all.jar <PATH>

Se quiser rodar sem passar um arquivo JSON e digitar a entrada manualmente, use:

- java -jar build/libs/capital-gains-calculator-1.0-SNAPSHOT-all.jar

✅ Rodar com Gradle

Se o Gradle estiver configurado corretamente, o comando é:
- ./gradlew run --args="input.json"

Se estiver no Windows e o Gradle não estiver instalado globalmente:
- gradlew.bat run --args="input.json"


O --args="input.json" arquivo tem que esta na raiz do projeto ou --args="<PATH>"

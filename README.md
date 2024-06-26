# Manual de Instalação, Compilação e Utilização

## Recursos Utilizados

Para o desenvolvimento do projeto, foram utilizados os seguintes recursos:

- **IDE:** Apache NetBeans 17
  - Disponível em: [Apache NetBeans 17](https://archive.apache.org/dist/netbeans/netbeans-installers/17/Apache-NetBeans17-bin-windows-x64.exe)
- **Sistema Operacional:** Windows 10 de 64 bits
- **Terminal de Comandos:** PowerShell (Windows)
- **Pacote Java:** Java versão "17.0.6" 2023-01-17 LTS
  - Java(TM) SE Runtime Environment (build 17.0.6+9-LTS-190)
  - Java HotSpot(TM) 64-Bit Server VM (build 17.0.6+9-LTS-190)
  - Disponível em:
    - [jdk-8u202-windows-x64](https://www.oracle.com/br/java/technologies/javase/javase8-archivedownloads.html#license-lightbox)
    - [jdk-17_windows-x64_bin](https://download.oracle.com/java/17/archive/jdk-17.0.6_windows-x64_bin.exe)

## Abrir o Projeto na IDE

1. Para acessar o projeto, é necessário abri-lo no NetBeans.
2. Inicialmente, descompacte o arquivo chamado `Projeto.rar`.
3. Na pasta `Projeto`, você encontrará duas pastas nomeadas `Exemplos_de_teste` e `ProjetoCompilador`. A primeira contém exemplos de teste feitos com o compilador e a segunda contém o projeto e todos os arquivos pertencentes a ele.
4. Na IDE, selecione **Abrir Projeto** → **Procurar a pasta onde o projeto foi descompactado** → Selecione a pasta `ProjetoCompilador` → **Abrir Projeto**.

## Compilação

1. Com o arquivo `Projeto.rar` descompactado, localize o arquivo chamado `Program.java`, que está na pasta `src`.
   - Exemplo: `...\Projeto\ProjetoCompilador\src\src\Program.java`
2. Com o Terminal de Comandos aberto, digite:
   ```shell
   javac -encoding UTF8 Program.java
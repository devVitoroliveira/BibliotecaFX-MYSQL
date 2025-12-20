# 📚 Sistema de Gerenciamento de Biblioteca

Este é um projeto de aplicação Desktop desenvolvido em **Java** utilizando **JavaFX** para a interface gráfica. O sistema tem como objetivo gerenciar as operações fundamentais de uma biblioteca, incluindo cadastro de pessoas (autores, clientes, funcionários), acervo de livros e controle de empréstimos.

## 🚀 Características e Arquitetura

O projeto segue o padrão de arquitetura **MVC (Model-View-Controller)** combinado com o padrão **DAO (Data Access Object)** para persistência de dados.

### Peculiaridades do Design

- **Herança no Banco de Dados:** O sistema utiliza um conceito de herança na modelagem de dados. As entidades `Autor`, `Cliente` e `Funcionario` herdam características de uma entidade base `Pessoa`. No código, isso é refletido nas classes DTO e nas queries SQL que utilizam `JOIN` para buscar dados completos.
- **Interface Responsiva:** Utilização de FXML para definição de layouts e CSS para estilização, garantindo uma aparência moderna e organizada.
- **Feedback Visual:** Uso de uma classe utilitária (`AlertUtils`) para padronizar todas as mensagens de erro, aviso e sucesso, garantindo que todos os pop-ups tenham o ícone da aplicação.

## 🛠️ Funcionalidades

O sistema é dividido em módulos acessíveis através de um menu principal:

1.  **Login:** Autenticação de usuários administrativos via banco de dados.
2.  **Gestão de Autores:** Cadastro completo com nacionalidade e período de vida.
3.  **Gestão de Clientes:** Cadastro com formatação automática de telefone e validação de e-mail.
4.  **Gestão de Funcionários:** Controle de equipe com validação de salário mínimo e associação de cargos.
5.  **Acervo de Livros:** Cadastro de obras vinculadas a autores, com validação rigorosa de ISBN.
6.  **Empréstimos:** Controle de saída e entrada de livros, vinculando clientes a obras com datas de início e fim.

## ✅ Validações e Regras de Negócio

O sistema implementa diversas validações para garantir a integridade dos dados antes da persistência:

### 📖 Livros (ISBN)

- **Verificação de Formato:** Aceita apenas ISBN-10 ou ISBN-13.
- **Cálculo de Checksum:** Implementa o algoritmo matemático para validar se os dígitos verificadores do ISBN são autênticos (Luhn algorithm/Mod 11).

### 📧 Contato (Email e Telefone)

- **E-mail:** Validação via Regex (`^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$`) para garantir formatos reais (ex: usuario@dominio.com).
- **Telefone:**
  - Máscara visual na tabela: Formata automaticamente para `(XX) XXXXX-XXXX`.
  - Validação de entrada: Garante que apenas números sejam inseridos e que tenha o comprimento correto (11 dígitos).

### 💰 Funcionários

- **Salário Mínimo:** Impede o cadastro de salários inferiores ao mínimo estipulado (R$ 1.520,00).
- **Tipagem:** Garante que campos numéricos não recebam texto.

### 📅 Datas

- **Formato:** Padronização para `dd-MM-yyyy`.
- **Lógica Temporal:**
  - O ano de publicação de um livro não pode ser futuro.
  - Datas de falecimento (autores) não podem ser anteriores ao nascimento.

## 💻 Ferramentas e Tecnologias

- **Linguagem:** Java (JDK 17+)
- **Interface Gráfica:** JavaFX 21 (FXML + CSS)
- **Banco de Dados:** MySQL
- **Conectividade:** JDBC (Java Database Connectivity)
- **Gerenciamento de Dependências:** Maven (Inferido pela estrutura de pastas)

## ⚙️ Configuração do Banco de Dados

Para rodar o projeto, é necessário configurar o banco de dados MySQL. A conexão padrão está configurada em `ConexaoDAO.java`:

- **URL:** `jdbc:mysql://localhost:3306/biblioteca`
- **Usuário:** `root`
- **Senha:** `admin`

### Estrutura de Tabelas Esperada (Resumo)

O sistema espera as seguintes tabelas principais:

- `pessoa` (Tabela pai para Autor, Cliente, Funcionario)
- `autor` (Vinculado a pessoa)
- `cliente` (Vinculado a pessoa)
- `funcionario` (Vinculado a pessoa e cargo)
- `cargo`
- `livro`
- `emprestimo`
- `user` (Para login)

## 🔐 Credenciais de Acesso ao Sistema

Para realizar o login no sistema, utilize as seguintes credenciais:

- **Usuário:** `admin`
- **Senha:** `1234`

## ⚠️ Avisos Comuns (Troubleshooting)

Ao executar o projeto, você pode encontrar um aviso no console similar a:

> `WARNING: Loading FXML document with JavaFX API of version 24.0.1 by JavaFX runtime of version 21.0.6`

Isso ocorre porque os arquivos FXML foram gerados com uma versão mais recente do JavaFX (Scene Builder 24) do que a versão da biblioteca utilizada no projeto (JavaFX 21, compatível com JDK 21). **O sistema funcionará normalmente**, pois o FXML é retrocompatível. A versão 24 do JavaFX exigiria o JDK 22, que pode não estar disponível no seu ambiente.

### Como resolver este aviso?

Se você deseja eliminar o aviso, tem duas opções:

1.  **Editar os arquivos FXML (Recomendado):** Abra os arquivos `.fxml` em um editor de texto e altere a tag `xmlns="http://javafx.com/javafx/24.0.1"` para `xmlns="http://javafx.com/javafx/21"`.
2.  **Atualizar o Ambiente:** Instale o **JDK 22** e atualize o `pom.xml` para usar a versão `24.0.1` do JavaFX e `release 22` no compilador.

## � Estrutura de Pastas

```
src/main/java/biblioteca/fx
├── Controller  # Lógica de interação com a tela (Listeners, Ações de botões)
├── DAO         # Acesso ao banco de dados (SQL, Conexão)
├── DTO         # Objetos de Transferência de Dados (Modelos)
└── util        # Utilitários (Alertas, Formatações)
```

---

Desenvolvido como parte de um projeto de sistema de gestão bibliotecária.

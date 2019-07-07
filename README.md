### Trabalho da disciplina de compiladores

Check-list de análise semântica

- [x] Declaração de Variáveis (inserir na hash)
- [x] Declaração de funções (inserir na hash)
- [x] Checar se a func foi declarada em funccall
- [x] Checar se as variáveis utilizadas durante o expr foram declaradas
- [x] Checar se as variáveis utilizadas durante o funccal foram declaradas
- [x] Checar o tipo das variáveis durante atribuições
- [x] Checar o tipo das variáveis em operações matemáticas
- [x] Checar o tipo das variáveis em operações de comparação
- [x] Checar se determinada função possui tipo de retorno igual ao tipo declarado
- [x] Checar se o programa possui uma função main
- [x] Checar se A quantidade e tipos de parâmetros na chamada de função/procedimento devem ser iguais aos utilizados na declaração das mesmas.
- [ ] Criar mais testes e debuggar!!!
- [ ] Checar se precisa checar mais coisas (kkk to triste)

TESTES A SEREM REALIZADOS:

- [X] Usar variável não declarada
- [X] Declarar variável mais de uma vez
- [X] Atribuir tipo de dado incompatível com variável
- [X] Função com retorno não estar associado a uma variável
- [X] Retorno de função incompatível com variável
- [X] Valor do return diferente do que a função deveria retornar
- [X] Chamar função que não foi declarada
- [X] Tipos de dados no parâmetro diferentes entre chamada e definição do escopo da Função
- [X] Quantidade de parâmetros na chamada da função deve ser igual a utilizada na declaração da função
- [X] Utilizar palavra chave como id
- [X] Funções predefinidas não precisam ser declaradas para poderem ser usadas, basta serem chamadas com id = readInt() ou id = readString(), por exemplo.
- [X] Tem que ter função main, e tem que ser sem parâmetro e sem Retorno
- [X] Redefinir tipo de variável (Atribuir valor de tipo diferente ao longo do código)
- [X] 2 = função(), 2+2 = 3*3 -> São validos sintaticamente, mas não na semântica. Uma expressão do tipo AssignExprStat tem que começar com id.

Erros a serem corrigidos:

- [X] problema com chamadas de funcao onde operadores nao estao separados com espaco, ex: fatorial(5)*5*fatorial(5)
- [ ] permitir declaracao da mesma variavel em escopos diferentes, ex:
      function f (tipo : Boolean) -> Int{
      if tipo == true{
         var teste : Int;
         teste = 1;
      }
      if tipo == false{
         var teste : Int;
         teste = 1;
      }
          return teste;
      }

- [X] nao permite condições com "and" e "or" para tipos nao booleanos, ex: if flag == true or x > 80    (x como um inteiro)

- [ ] nao permite operacoes do tipo: a = x == 5 or y;
   onde "a" deveria receber true caso a condicao fosse verdadeira
   talvez o mesmo raciocinio para: return a == b
   onde retorna 1 se a == b

- [ ] permitir comentarios com /* e */
- [X] problema no retorno de funcao, em que retorna uma string com nome de funcao, ex:
      function f(x : Int) -> String{
         return "write";
      }
   nao apareceu a segunda aspa no codigo gerado
= [ ] a = b + c + d, onde todos estas variáveis são do tipo string


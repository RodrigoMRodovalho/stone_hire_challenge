![StoneSDK](https://cloud.githubusercontent.com/assets/2567823/11539067/6300c838-990c-11e5-9831-4f8ce691859e.png)

# Desafio Mobile

O desafio consiste em criar uma loja de itens de Star Wars que o usuário é capaz de adicionar os itens desejados em um carrinho de compras e finalizar a compra com uma simulação de transação e-commerce.

O candidato deve dar **fork** neste repositório e após o termino do desenvolvimento, realizar um **pull request** para análise do time.

Para obter os itens da loja, sua aplicação deverá realizar uma chamada `GET` na URL https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json

A lista de itens deve exibir as seguintes informações:
+ Nome [title]
+ Preço [price]
+ Vendedor [seller]
+ Foto do item [thumbnailHd]

Após o usuário adicionar todos os itens no carrinho, ele deverá finalizar a compra.
Para finalizar a compra, aconselhamos que use o [Apiary](https://apiary.io) como API nessa etapa.

###### Simulação E-commerce

Sua aplicação deve realizar um `POST` com os seguintes atributos:
+ Número do cartão (máximo de 16 números - XXXX XXXX XXXX XXXX)
+ Nome do portador do cartão
+ Vencimento do cartão (MM/yy)
+ CVV (código encontrado na parte traseira do cartão)
+ Valor da transação (total dos itens no carrinho)

``` json
{  
   "card_number":"1234123412341234",
   "value":7990,
   "cvv":789,
   "card_holder_name":"Luke Skywalker",
   "exp_date":"12/24"
}
```

###### Banco de dados
Todas as transações realizadas devem ser salvas em um banco interno com os seguintes campos:


+ Valor
+ Data e hora
+ Últimos 4 dígitos do cartão
+ Nome do portador do cartão

###### Lista de Transações
A aplicação deverá conter uma tela para exibir as transações que foram salvas em seu banco de dados.
 
---
#### LICENSE
```
MIT License

Copyright (c) 2016 Stone Pagamentos

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

#### Rodrigo Rodovalho 

#Stone Challenge


Desafio Mobile - Stone

App: Stone Wars

O app Stone Wars foi construido seguindo conceitos de:

    * Clean Architecture
    * S.O.L.I.D
    * Model-View-Presenter

Stone Wars foi estruturado da seguinte maneira:

    - 3 Módulos (camadas)
        * data (Android Library)
        * domain (Java Library)
        * presentation (Android Application)

    
    - Módulo data: Repositório ( Chamadas API, Banco de Dados )
    - Módulo domain: Definição dos casos de uso
    - Módulo presentation: Camada de apresentação Android, seguindo MVP. 

    
    - Os módulos "se falam" através de injeção de dependência utilizando Dagger 2


Dependências (As dependências estão declaradas no arquivo dependencies.gradle):

    + dagger2 - Framework de Injeção de dependência
    + rxjava2 - Framework de programação reativa
    + lombok - Biblioteca para geração automática de código para java (utilizei em POJO's)
    + retrofit - Biblioteca para comunicação REST
    + butterknife - Blblioteca para injeção de Views
    + entre outras


    + junit - Testes unitários
    + mockito, powermock - Bibliotecas de testes para criação de Mocks
    + hamcrest - Biblioteca com matchers para criação de assertions
    + espresso - Testes instrumentais
    
    
   


Plugins:

    + retrolambda - Biblioteca que compatibilidade de lambda para versões do Java < 8
    + realm - Mobile Database


#Testes

    * Unitários (JUnit, mockito, powermock, hamcrest)
        - Maioria dos presenters
        - Objeto que geerencia o carrinho de compras
        - Alguns casos de uso

    * instrumentais (Espresso)
        - Algumas activities

# IMPORTANTE:

Devido ao pouco tempo disponível que eu tive para realizar o desafio dentro dos 5 dias, eu escolhi por priorizar 
a estrutura de código, legibilidade, manutenabilidade e boas práticas de programação e por dar menos atenção a 
questão de layouts e resources. Por exemplo, eu não gerei resouces (imagem) para várias resoluções. Por não priorizar
esse aspecto importante em um aplicativo, que é a UI/UX, o conceito de Perfect Pixel Eye ficou prejudicado.

Pelo mesmo motivo de tempo, algumas features não foram implementadas, são elas:

    - Suporte a rotação de tela
    - Suporte a múltiplos dispositivos (phone e tablet)
    - Melhoria da esperiência com animações
    - Bug na tela de carrinho de compras ao clicar em remover o item, dá um bug na recylcerview
    - Melhorar os feedbacks de erro
    - Corrigir o fluxo de navegação quando uma transação é aprovada.
    

# OBS:

Na funcionalidade de salvar a transação no BD, pede-se que seja armazenada a data e hora da transação, esse dado poderia
ter vindo na resposta do servidor para concluir a transação, mas por questão de simplicidade e tempo, optei por na hora 
que receber a resposta, obter esse dado do sistema e daí persistir no BD. 


# DRAWBACK de Arquitetura

Visando o maior desacoplamento entre os módulos, algumas classes de modelo são idênticas ou muito parecidas entre os módulos, 
necessitando então de uma transformação entre modelos. 


Infelizmente não consegui implementar tudo que eu queria no prazo. Por exemplo, ter terminado todos os testes.

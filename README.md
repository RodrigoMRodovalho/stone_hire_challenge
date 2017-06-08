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
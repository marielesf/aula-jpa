#PARTE 1 - ENTIDADE JPA

Uma entidade JPA é uma classe Java simples que recebe algumas anotações e seus campos são persistidos em um BD. Qualquer classe com anotações **@Entity** e um atributo **@Id** e que possui um construtor sem argumentos pode ser uma entidade JPA.

##LEVANTANDO A INSTÂNCIA DO BANCO DE DADOS
Vá em Run > Run Configurations > Java Aplication (clique em cima com o Botão Direito) New >> e insira os valores:
 a. Name: **HSQLDB**
 b. Project: **Selecione aula-jpa**
 c. Main Class: **org.hsqldb.server.Server** 
 d. **Apply**
 e. **Run**
 
##CONFIGURANDO A CONEXÃO COM O BANCO DE DADOS 
Visualize o arquivo **src/META-INF/persistence.xml**. Nele está configurado a **Unidade de Persistência** (persistence-unity). No nosso exemplo, estamos realizando conexão com o banco HSQLDB.

##CRIANDO UMA ENTIDADE JPA
1. Com o botão direito em cima do projeto, selecione **New > JPA Entity** e insira os valores:
 
a. package: **br.edu.ifrs.canoas.tads.lds.pojo**

b. classe: **Usuario**

c. Clique em **Finish**
 
2. Adicione os atributos 
* **Long id**
* **String email**
* **String senha**
* ... e gere os getters e setters.

> As anotações mínimas de uma entidade JPA são: @Entity que é usada para definir a classe como uma entidade JPA e a anotação @Id que é usada para indicar que é uma PK da entidade.

> Quanto a geração da chave primária, JPA permite que o programador escolha algumas estratégias, como:

> * **GenerationType.AUTO**: será selecionada automaticamente uma estratégia para geração de chave primária.

> * **GenerationType.IDENTITY**: indica que é necessário ler novamente a linha inserida no banco de dados para recuperar a chave gerada pelo próprio banco e atualizar o ID da entidade JPA 

> * **GenerationType.SEQUENCE**: indica que deve ser usada uma sequence do banco de dados para a geração da chave primária da entidade.

> * **GenerationType.TABLE**: indica que uma tabela do banco de dados deve ser usada para gerar a chave primária da entidade. 

3. Use a estratégia AUTO para o atributo **id**:

> ` @GeneratedValue(strategy = GenerationType.AUTO)


##ADICIONANDO A CLASSE AO CONTEXTO DE PERSISTÊNCIA

Atualize o arquivo Persistence Unity adicionando a entidade criada entre </provider> e <properties> com:

> `<class>br.edu.ifrs.canoas.tads.lds.aulajpa.pojo.Usuario</class>

##EXERCÍCIO

1. Descomente o método br.edu.ifrs.canoas.tads.lds.aulajpa.pojo.UsuarioTest.test01() e o faça executar sem erro.

2. Descomente o método br.edu.ifrs.canoas.tads.lds.aulajpa.pojo.UsuarioTest.test02() e o faça executar sem erro.


#PARTE 2 - GERENCIANDO ENTIDADES

A interface **EntityManager** é quem faz a persistência dos objetos Java em tabelas do BD, adicionando-os ao contexto de persistência. Note que não se pode ter duas entidades com mesmo nome dentro de um contexto (por exemplo, não se pode ter duas classes com nome Usuario). 

Para se obter o **EntityManager**, primeiro é necessário criar uma fábrica **EntityManagerFactory** que faz referência a uma unidade de persistência específica  descrita no arquivo **persistence.xml**. Deve-se criar uma única vez a fábrica e a partir dela, pode-se obter o **EntityManager**.

> Com base no exposto, analise o código br.edu.ifrs.canoas.tads.lds.aulajpa.model.util.EntityManagerUtil.



#PARTE 3 - TRABALHANDO COM DQL E DML

A persistência de uma entidade é uma operação que pega um objeto transiente (que ainda não foi salvo no banco de dados) e o armazena.

## Busca

Uma forma de buscar um elemento do banco de dados é passar sua chave:

>` Usuario usuario = em.find(Usuario.class, 1L);  //busca do banco (faz o SELECT)

## Exclusão

Para excluir uma entidade, primeiro deve-se primeiro colocá-la no contexto de persistência (recém salvar a entidade ou recuperá-la do banco sem fechar o EM - cada vez que o EM é fechado, libera-se as entidades deste contexto de persistência) para depois excluí-la.

>` Usuario usuario = em.find(Usuario.class, 1L); //busca do banco a entidade e a coloca no contexto de persistência 
>` em.remove(usuario); //uma vez no contexto de persistência, faz o DELETE no banco

Sempre depois de usar o **EntityManager**, deve-se fechá-lo usando o método **close()**, encerrando o contexto de persistência e liberando todas as entidades (que mudam de **managed** para **detached**). Mudanças em entidades **detached** não são refletidas no BD.

## Atualização

Tem duas principais formas de atualizar uma entidade: 

1. Buscando a entidade do banco (pelo método **find()**), alterando sua situação de **detached** para **managed**, e depois a atualizando sem remover do contexto de persistência

>` Usuario usuario = em.find(Usuario.class, 1L); //busca do banco a entidade e a coloca no contexto de persistência 
>` usuario.setSenha("NovaSenha");//atualiza no banco; //faz o UPDATE no banco

2. Atualizando normalmente uma entidade, depois criando um EM e executando a operação **merge()**.

>` usuario.setSenha("NovaSenha");//Já existe um usuario e ele não está em nenhum contexto de persistência
>` EntityManager em2 = emf.createEntityManager(); //Cria um novo contexto de persistência
>` em2.merge(usuario); //vincula o objeto modificado ao novo contexto, fazendo com que seja atualizado no banco (executando o UPDATE)


**OBS IMPORTANTE** Rodando no Java SE, é necessário sempre gerenciar (iniciar e fechar) transações de manipulação (persist, remove e merge).

```java
 usuario.setSenha("NovaSenha");
 EntityManager em2 = emf.createEntityManager(); 
 em2.getTransaction().begin(); //Inicia transação
 em2.merge(usuario);
 em2.getTransaction().begin(); //Fecha transação
 em2.close(); //fecha EM e libera as entidades (as torna **detached**)
```

## Java Persistence Query Language (JPQL)

É possível fazer consulta com uma TypedQuery:

```
 em = EntityManagerUtil.getEM();
 TypedQuery<Usuario> query = em.createQuery(
         "SELECT usr FROM Usuario usr"
         + " where usr.nome = :nome"
         + " and usr.id = :id", Usuario.class);
 
 if (usuario != null) {
     if (usuario.getNome() != null) {
       query.setParameter("nome", usuario.getNome());
     }
     if (usuario.getId() != null) {
       query.setParameter("id", usuario.getId());
     }
 }
 
 List<Usuario> usuarios = query.getResultList();
```

#PARTE 4 - PADRÃO DAO (Data Access Object)

A ideia do DAO é isolar as operações JDBC, como criação, recuperação, atualização e exclusão, isolando-as das demais camadas da aplicação. Basicamente é trabalhar o que vimos até agora para cada entidade.

1. Descomente todos os métodos da classe br.edu.ifrs.canoas.tads.lds.aulajpa.pojo.model.dao.UsuarioDAOTest e os façam executar sem erro.


#PARTE 5 - RELACIONANDO ENTIDADES

Em JPA, devemos trabalhar com o conceito de multiplicidade e navegabilidade utilizando os seguintes mapeamentos: **many-to-one** (muitos para um), **one-to-one** (um para um), **one-to-many** (um para muitos) e **many-to-many** (muitos para muitos).

## Many-to-One
Considere o Modelo Relacional abaixo:

> USUARIO (ID, EMAIL, SENHA)
> TELEFONE(ID, ID_USUARIO, CODIGO, NUMERO)

E a estrutura de classes:

> Classe Usuario, com os atributos id (Long), email(String) e senha(String).
> Classe Telefone, com os atributos id (Long) , usuario (Usuario), codigo (Integer) e numero (Integer).

Esta estrutura pode gerar o seguinte mapeamento:

```java
 public class Telefone{
     //...
     @ManyToOne
     @JoinColumn(name="ID_USUARIO") 
     private Usuario usuario; 
     //...
 }
```

## One-to-One
Considere o Modelo Relacional abaixo:

> USUARIO (ID, EMAIL, SENHA, ID_ENDERECO)
> ENDERECO (ID, LOGRADOURO, CIDADE, ESTADO)

E a estrutura de classes:

> Classe Usuario, com os atributos id (Long), email(String), senha(String), endereco (Endereco).
> Classe Endereco, com os atributos id (Long), Logradouro (String), cidade (String), estado (String)

Esta estrutura deve gerar o seguinte mapeamento:

```java
 public class Usuario{
     //...
     @OneToOne
     @JoinColumn(name="ID_ENDERECO") 
     private Endereco endereco; 
     //...
 }
```

Para um relacionamento bidirecional (a partir de endereco, acessar também os usuários), basta utilizar este código:

```java
 public class Endereco{
     //...
     @OneToOne(mappedBy="endereco") 
     private Usuario usuario; 
     //...
 }
```

## One-to-Many

É o relacionamento oposto ao **Many-to-One**, portanto é só fazer as devidas anotações nas classes. Neste caso, é possível recuperar todos os telefones de um usuário a partir dele próprio.

```java
 public class Usuario{
     //...
     @OneToMany (mappedBy="telefone")
    private Collection<Telefone> telefones; 
     //...
 }
```


## Many-to-Many

Considere o Modelo Relacional abaixo:

> USUARIO (ID, EMAIL, SENHA)
> GRUPO (ID, DESCRICAO)
> GRUPO_USUARIO (ID, USUARIO_ID, GRUPO_ID)

E a estrutura de classes:

> Classe Usuario, com os atributos id (Long), email(String), senha(String), grupos (Collection<Grupo>)
> Classe Grupo, com os atributos id (Long), descricao (String)

Esta estrutura deve gerar o seguinte mapeamento da tabela associativa:

```java
 public class Usuario{
     //...
 	   @ManyToMany
     @JoinTable(name="GRUPO_USUARIO", 
                joinColumns=@JoinColumn(name="USUARIO_ID"),
                inverseJoinColumns=
                          @JoinColumn(name="GRUPO_ID")
                )
     private Collection<Grupo> grupos;
     //...
 }
``` 

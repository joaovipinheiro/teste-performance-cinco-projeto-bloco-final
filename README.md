# Sistema de Gerenciamento de Pessoas

Sistema Spring Boot para gerenciamento de pessoas com integração entre sistemas e testes automatizados.

## 🏗️ Arquitetura

O sistema é composto por dois módulos principais:

### 1. Sistema Principal (PersonService)
- **PersonService**: Serviço de negócio para gerenciamento de pessoas
- **PersonController**: Controller web para interface HTML
- **PersonRepository**: Repositório em memória para persistência

### 2. Sistema de Integração (SystemIntegration)
- **SystemIntegration**: Camada de integração para comunicação com sistemas externos
- **PersonIntegrationController**: Controller REST para API de integração
- **DataSyncService**: Serviço de sincronização de dados
- **IntegrationEventPublisher**: Publicador de eventos de integração

## 🔄 Integração entre Sistemas

Os dois sistemas estão integrados através de:

1. **Compartilhamento de Repositório**: Ambos usam o mesmo `PersonRepository` para garantir consistência
2. **Eventos de Integração**: `IntegrationEventPublisher` notifica mudanças entre sistemas
3. **API REST**: `PersonIntegrationController` expõe endpoints para integração externa

### Fluxo de Sincronização

```
PersonService (Sistema Principal)
    ↓
Cria/Atualiza/Deleta Pessoa
    ↓
IntegrationEventPublisher.publishEvent()
    ↓
SystemIntegration (Sistema de Integração)
    ↓
Atualiza dados compartilhados
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+

### Executar a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em:
- Interface Web: http://localhost:8080
- API REST: http://localhost:8080/api/persons

## 🧪 Testes

### Executar todos os testes
```bash
mvn test
```

### Executar testes de integração
```bash
mvn test -Dtest="**/*IntegrationTest"
```

### Executar testes unitários
```bash
mvn test -Dtest="**/*Test" -Dtest="!**/*IntegrationTest"
```

### Executar testes com cobertura
```bash
mvn test jacoco:report
```

## 📊 Endpoints da API

### Sistema Principal (HTML)
- `GET /` - Redireciona para lista de pessoas
- `GET /persons` - Lista todas as pessoas (HTML)
- `GET /person/create` - Formulário de criação
- `POST /person/create` - Cria nova pessoa
- `GET /person/edit/{id}` - Formulário de edição
- `POST /person/edit/{id}` - Atualiza pessoa
- `POST /person/delete/{id}` - Deleta pessoa

### Sistema de Integração (REST API)
- `GET /api/persons` - Lista todas as pessoas (JSON)
- `GET /api/persons/{id}` - Busca pessoa por ID
- `POST /api/persons` - Cria nova pessoa
- `PUT /api/persons/{id}` - Atualiza pessoa
- `DELETE /api/persons/{id}` - Deleta pessoa

## 🔧 GitHub Actions

O projeto inclui workflows automatizados:

### CI - Build and Test (`ci.yml`)
- Executa em push e pull requests
- Build e testes automatizados
- Geração de relatórios de teste

### Full CI (`full-ci.yml`)
- Build completo com package
- Testes unitários e de integração
- Análise de código
- Cobertura de testes

### Static Analysis (`static-analysis.yml`)
- Análise estática com SpotBugs
- Verificação de estilo com Checkstyle
- Execução semanal agendada

## 📝 Princípios Aplicados

- ✅ **Imutabilidade**: Classe `Person` é imutável
- ✅ **Separação de Consultas e Modificadores**: Métodos claramente separados
- ✅ **Polimorfismo**: Uso de Optional e exceções específicas
- ✅ **Reutilização**: Componentes compartilhados entre sistemas
- ✅ **Testes Guiados**: Refatoração guiada por testes

## 🛠️ Tecnologias

- Spring Boot 3.3.2
- Java 17
- Maven
- JUnit 5
- JQwik (Property-based testing)
- Selenium WebDriver
- SLF4J (Logging)

## 📄 Licença

Este projeto é um exemplo educacional.


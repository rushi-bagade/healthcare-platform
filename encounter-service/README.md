# Encounter Service

Healthcare EncounterMS for the healthcare microservices POC.

## Port and database

- Service port: `8083`
- Database: `encounter_db`
- Appointment Service: `http://localhost:8082`

Run `db-setup.sql` once in MySQL, then change `spring.datasource.password` in `src/main/resources/application.properties`.

## What this service does

An encounter represents the clinical visit/documentation associated with an appointment.

The service keeps only IDs for data owned by other services. It does not create JPA relationships to Patient, Doctor, or Appointment entities in other microservices.

When an encounter is created, EncounterMS calls AppointmentMS with `RestClient`, gets the appointment details, and copies the appointment's patient, doctor, and date into the encounter. A cancelled or no-show appointment cannot create an encounter. One appointment can have at most one encounter.

## Project structure

```text
com.healthcare.encounter
├── client
│   ├── dto
│   │   └── AppointmentResponse.java
│   └── AppointmentServiceClient.java
├── config
│   └── RestClientConfig.java
├── controller
│   └── EncounterController.java
├── dto
│   ├── request
│   │   ├── EncounterRequest.java
│   │   ├── EncounterStatusUpdateRequest.java
│   │   └── EncounterUpdateRequest.java
│   └── response
│       └── EncounterResponse.java
├── entity
│   └── Encounter.java
├── enums
│   └── EncounterStatus.java
├── exception
│   ├── AppointmentNotFoundException.java
│   ├── DuplicateEncounterException.java
│   ├── EncounterNotFoundException.java
│   ├── ErrorResponse.java
│   ├── ExternalServiceException.java
│   ├── GlobalExceptionHandler.java
│   └── InvalidEncounterStateException.java
├── mapper
│   └── EncounterMapper.java
├── repository
│   └── EncounterRepository.java
├── service
│   ├── EncounterService.java
│   └── impl
│       └── EncounterServiceImpl.java
└── EncounterServiceApplication.java
```

## APIs

### Create

```http
POST http://localhost:8083/v1/encounters
Content-Type: application/json
```

```json
{
  "appointmentId": 2,
  "chiefComplaint": "Persistent headache for three days",
  "diagnosis": "Migraine",
  "clinicalNotes": "Patient reports intermittent headache with sensitivity to light.",
  "treatmentNotes": "Advised hydration, rest and prescribed medication as per doctor assessment."
}
```

### Get by id

```http
GET http://localhost:8083/v1/encounters/1
```

### Get all with pagination and sorting

```http
GET http://localhost:8083/v1/encounters?page=0&size=10&sortBy=encounterDate&direction=desc
```

### Get by patient

```http
GET http://localhost:8083/v1/encounters/patients/3?page=0&size=10&sortBy=encounterDate&direction=desc
```

### Get by doctor

```http
GET http://localhost:8083/v1/encounters/doctors/5?page=0&size=10&sortBy=encounterDate&direction=desc
```

### Get by appointment

```http
GET http://localhost:8083/v1/encounters/appointments/2
```

### Update clinical information

```http
PUT http://localhost:8083/v1/encounters/1
Content-Type: application/json
```

```json
{
  "chiefComplaint": "Persistent headache and nausea",
  "diagnosis": "Migraine",
  "clinicalNotes": "Symptoms improved after treatment. No red-flag symptoms reported.",
  "treatmentNotes": "Continue current treatment and follow-up as advised."
}
```

### Update status

```http
PATCH http://localhost:8083/v1/encounters/1/status
Content-Type: application/json
```

```json
{
  "status": "COMPLETED"
}
```

## Status rules

- New encounter starts as `OPEN`.
- `OPEN` can become `COMPLETED` or `CANCELLED`.
- `COMPLETED` is terminal in this POC.
- `CANCELLED` cannot be reopened.
- Completed/cancelled encounters cannot be edited through PUT.

## Integration flow

```text
Client / Receptionist / Doctor
          |
          v
   Encounter Service
          |
          +----> Appointment Service (REST)
          |
          +----> MySQL (encounter_db)
```

## Run

With Maven installed:

```bash
mvn spring-boot:run
```

Or build:

```bash
mvn clean package
java -jar target/encounter-service-0.0.1-SNAPSHOT.jar
```

Health endpoint:

```http
GET http://localhost:8083/actuator/health
```

Important: build/runtime compilation was not executed in this generation environment because Maven and external dependency downloads are unavailable there. The project is structured as a normal Spring Initializr Maven project for local use.

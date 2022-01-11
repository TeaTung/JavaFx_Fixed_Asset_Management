CREATE
DATABASE FIXED_ASSETS_DATABASE
USE FIXED_ASSETS_DATABASE;


CREATE TABLE tbUnit
(
    UnitId   VARCHAR(10) PRIMARY KEY,
    UnitName NVARCHAR(100),
    Note     NVARCHAR(200),
)

CREATE TABLE tbDeviceType
(
    TypeId   VARCHAR(10) PRIMARY KEY,
    TypeName NVARCHAR(100),
    Note     NVARCHAR(200),
)

CREATE TABLE tbDeviceModel
(
    ModelId       VARCHAR(10) PRIMARY KEY,
    UnitId        VARCHAR(10),
    TypeId        VARCHAR(10),
    Quantity      INT,
    ModelName     NVARCHAR(100),

    CONSTRAINT FK_DEVICE_MODEL_TO_UNIT FOREIGN KEY (UnitId)
        REFERENCES tbUnit (UnitId),

    CONSTRAINT FK_DEVICE_MODEL_TO_DEVICE_TYPE FOREIGN KEY (TypeId)
        REFERENCES tbDeviceType (TypeId),
)



CREATE TABLE tbDevice
(
    DeviceId        VARCHAR(10) PRIMARY KEY,
    ModelId         VARCHAR(10),
    DeviceName      VARCHAR(40),
    DeviceStatus    NVARCHAR(50),
    ContractId      VARCHAR(10),
    YearUsed        VARCHAR(10),
    YearManufacture VARCHAR(10),
    Price           FLOAT,
    Specification   TEXT,
    PercentDamage   FLOAT,
    QuantityDevice  INT,

    CONSTRAINT FK_DEVICE_TO_DEVICE_MODEL FOREIGN KEY (ModelId)
        REFERENCES tbDeviceModel (ModelId),
    CONSTRAINT FK_DEVICE_TO_CONTRACT FOREIGN KEY (contractId)
        REFERENCES tbContract (contractId),
)

CREATE TABLE tbProvider
(
    ProviderId   VARCHAR(10) PRIMARY KEY,
    ProviderName NVARCHAR(100),
    Address      NVARCHAR(100),
    Phone        VARCHAR(15),
    Email        VARCHAR(30)
)

CREATE TABLE tbContract
(
    ContractId VARCHAR(10) PRIMARY KEY,
    ProviderId VARCHAR(10),
    ImportDate NVARCHAR(20),
    Note       TEXT,

    CONSTRAINT FK_CONTRACT_TO_PROVIDER FOREIGN KEY (ProviderId)
        REFERENCES tbProvider (ProviderId),
)

CREATE TABLE tbImportNote
(
    ImportId       VARCHAR(10) PRIMARY KEY,
    TypeId         VARCHAR(10),
    ContractId     VARCHAR(10),
    ImportQuantity INT,
    ImportDate     DATE,

    CONSTRAINT FK_IMPORT_NOTE_TO_DEVICE FOREIGN KEY (TypeId)
        REFERENCES tbDeviceType (TypeId),

    CONSTRAINT FK_IMPORT_NOTE_TO_CONTRACT FOREIGN KEY (ContractId)
        REFERENCES tbContract (ContractId),
)


CREATE TABLE tbInventory
(
    InventoryId   VARCHAR(10) PRIMARY KEY,
    DeviceId      VARCHAR(10),
    UsableValue   INT,
    InventoryDate DATE,

    CONSTRAINT FK_INVENTORY_TO_DEVICE FOREIGN KEY (DeviceId)
        REFERENCES tbDevice (DeviceId),
)

CREATE TABLE tbLiquidation
(
    LiquidationId   VARCHAR(10) PRIMARY KEY,
    DeviceId        VARCHAR(10),
    LiquidationDate DATE,

    CONSTRAINT FK_LIQUID_ FOREIGN KEY (DeviceId)
        REFERENCES tbDevice (DeviceId),
)
CREATE TABLE tbDepartment
(
    DepartmentId   VARCHAR(10) PRIMARY KEY,
    DepartmentName NVARCHAR(100),
)
CREATE TABLE tbDeliveryNote
(
    DeliveryId   VARCHAR(10) PRIMARY KEY,
    DeviceId     VARCHAR(10),
    DepartmentId VARCHAR(10),
    DeliveryDate DATE,

    CONSTRAINT FK_DEPARTMENT FOREIGN KEY (DepartmentId)
        REFERENCES tbDepartment (DepartmentId),

    CONSTRAINT FK_DE FOREIGN KEY (DeviceId)
        REFERENCES tbDevice (DeviceId),
)

CREATE TABLE tbPerson
(
    PersonId     VARCHAR(10) PRIMARY KEY,
    DepartmentId VARCHAR(10),
    Name         NVARCHAR(100),
    Title        NVARCHAR(100),
    CONSTRAINT FK_DEPART FOREIGN KEY (DepartmentId)
        REFERENCES tbDepartment (DepartmentId),
)



CREATE TABLE tbPersonAndLiquidation
(
    LinkId        VARCHAR(10) PRIMARY KEY,
    PersonId      VARCHAR(10),
    LiquidationId VARCHAR(10),

    CONSTRAINT FK_PERSON_AND_LIQUID_TO_PERSON FOREIGN KEY (PersonId)
        REFERENCES tbPerson (PersonId),

    CONSTRAINT FK_PERSON_AND_LIQUID_TO_LIQUID FOREIGN KEY (LiquidationId)
        REFERENCES tbLiquidation (LiquidationId)
)



CREATE TABLE tbPersonAndInventory
(
    LinkId      VARCHAR(10) PRIMARY KEY,
    PersonId    VARCHAR(10),
    InventoryId VARCHAR(10),

    CONSTRAINT FK_PERSON_AND_INV_TO_PERSON FOREIGN KEY (PersonId)
        REFERENCES tbPerson (PersonId),

    CONSTRAINT FK_PERSON_AND_INV_TO_INV FOREIGN KEY (InventoryId)
        REFERENCES tbInventory (InventoryId),
)



CREATE TABLE tbRepair
(
    FixId      VARCHAR(10) PRIMARY KEY,
    DeviceId   NVARCHAR( MAX),
    RepairDate VARCHAR(15),
    Company    NVARCHAR(100),
    Price      FLOAT,

)

CREATE TABLE tbTransfer
(
    TransferId VARCHAR(10) PRIMARY KEY,
    Department NVARCHAR(50),
    DeviceId   NVARCHAR( MAX),

)


CREATE DATABASE FIXED_ASSETS_DATABASE
USE FIXED_ASSETS_DATABASE;

CREATE TABLE tbUnit(
	UnitId VARCHAR(10) PRIMARY KEY,
	UnitName NVARCHAR(100),
	Note NVARCHAR(200),
)

CREATE TABLE tbDeviceType(
	TypeId VARCHAR(10) PRIMARY KEY,
	TypeName NVARCHAR(100),
	Note NVARCHAR(200),
)

CREATE TABLE tbDeviceModel(
	ModelId VARCHAR(10) PRIMARY KEY,
	UnitId VARCHAR(10),
	TypeId VARCHAR(10),
	Specification NVARCHAR(2000),
	Quantity INT,
	ModelName NVARCHAR(100),

	CONSTRAINT FK_DEVICE_MODEL_TO_UNIT FOREIGN KEY (UnitId)
    REFERENCES tbUnit(UnitId),

	CONSTRAINT FK_DEVICE_MODEL_TO_DEVICE_TYPE FOREIGN KEY (TypeId)
    REFERENCES tbDeviceType(TypeId),
)



CREATE TABLE tbDevice(
	DeviceId VARCHAR(10) PRIMARY KEY,
	ModelId VARCHAR(10),
	DeviceStatus NVARCHAR(50),
	YearUsed VARCHAR(10),
	YearManufacture VARCHAR(10),
	Price FLOAT,
	PercentDamage FLOAT,

	CONSTRAINT FK_DEVICE_TO_DEVICE_MODEL FOREIGN KEY (ModelId)
    REFERENCES tbDeviceModel(ModelId),
)

CREATE TABLE tbProvider(
	ProviderId VARCHAR(10) PRIMARY KEY,
	ProviderName NVARCHAR(100),
	Address NVARCHAR(100),
	Phone VARCHAR(15),
)

CREATE TABLE tbContract(
	ContractId VARCHAR(10) PRIMARY KEY,
	ProviderId VARCHAR(10),

	CONSTRAINT FK_Provider FOREIGN KEY (ProviderId)
    REFERENCES tbProvider(ProviderId),
)

CREATE TABLE tbImportNote(
	ImportId VARCHAR(10) PRIMARY KEY,
	TypeId VARCHAR(10),
	ContractId VARCHAR(10),
	ImportQuantity INT,
	ImportDate DATETIME,

	CONSTRAINT FK_Import FOREIGN KEY (TypeId)
    REFERENCES tbDeviceType(TypeId),

	CONSTRAINT FK_Contract FOREIGN KEY (ContractId)
    REFERENCES tbContract(ContractId),
)

CREATE TABLE tbInventory(
	InventoryId VARCHAR(10) PRIMARY KEY,
	DeviceId VARCHAR(10),
	UsableValue INT,
	InvertoryDate DATETIME,

	CONSTRAINT FK_Device FOREIGN KEY (DeviceId)
    REFERENCES tbDevice(DeviceId),
)

CREATE TABLE tbLiquidation(
	LiquidationId VARCHAR(10) PRIMARY KEY,
	DeviceId VARCHAR(10),
	LiquidationDate DATETIME,

	CONSTRAINT FK_Device_cons FOREIGN KEY (DeviceId)
    REFERENCES tbDevice(DeviceId),
)

CREATE TABLE tbDeliveryNote(
	DeliveryId VARCHAR(10) PRIMARY KEY,
	DeviceId VARCHAR(10),
	DepartmentId VARCHAR(10),
	DeliveryDate DATETIME,

	CONSTRAINT FK_Device_deliv_note FOREIGN KEY (DeviceId)
    REFERENCES tbDevice(DeviceId),
)

CREATE TABLE tbPerson(
	PersonId VARCHAR(10) PRIMARY KEY,
	DepartmentId VARCHAR(10),
	Name NVARCHAR(100),
	Title NVARCHAR(100),
)

CREATE TABLE tbPersonAndLiqudation(
	LinkId VARCHAR(10) PRIMARY KEY,
	PersonId VARCHAR(10),
	LiquidationId VARCHAR(10),

	CONSTRAINT FK_Person FOREIGN KEY (PersonId)
    REFERENCES tbPerson(PersonId),

	CONSTRAINT FK_Liquidation FOREIGN KEY (LiquidationId)
    REFERENCES tbLiquidation(LiquidationId),
)

CREATE TABLE tbPersonAndInventory(
	LinkId VARCHAR(10) PRIMARY KEY,
	PersonId VARCHAR(10),
	InventoryId VARCHAR(10),

	CONSTRAINT FK_Person_Inven FOREIGN KEY (PersonId)
    REFERENCES tbPerson(PersonId),

	CONSTRAINT FK_Inventory FOREIGN KEY (InventoryId)
    REFERENCES tbInventory(InventoryId),
)


CREATE TABLE tbFix(
	FixId VARCHAR(10) PRIMARY KEY,
	DeviceId VARCHAR(10),
	Confirmation NVARCHAR(100),
	Company NVARCHAR(100),
	Price FLOAT,

	CONSTRAINT FK_Device_Fix FOREIGN KEY (DeviceId)
    REFERENCES tbDevice(DeviceId),
)


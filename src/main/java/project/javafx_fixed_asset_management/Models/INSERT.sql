select * from tbDeviceType
select * from tbDeviceModel
select * from tbDevice


insert into tbDevice(DeviceId, ModelId, DeviceStatus, YearUsed, YearManufacture, Price, PercentDamage, DeviceName, Specification) values ('device1', 'model1', 'using', '2019', '2018', '1000000', '10', 'television', 'really modern');
insert into tbDevice(DeviceId, ModelId, DeviceStatus, YearUsed, YearManufacture, Price, PercentDamage, DeviceName, Specification) values ('device2', 'model2', 'using', '2019', '2018', '1000000', '10', 'television', 'really modern');
insert into tbDevice(DeviceId, ModelId, DeviceStatus, YearUsed, YearManufacture, Price, PercentDamage, DeviceName, Specification) values ('device3', 'model3', 'using', '2019', '2018', '1000000', '10', 'mobile', 'really fancy');

insert into tbUnit (unitId, unitName, note ) values ( 'unit1', 'DIEN THOAI A', 'none' );
insert into tbUnit (unitId, unitName, note ) values ( 'unit2', 'DIEN THOAI B', 'none' );
insert into tbUnit (unitId, unitName, note ) values ( 'unit3', 'DIEN THOAI CX', 'none' );
insert into tbUnit (unitId, unitName, note ) values ( 'unit5', 'DIEN THOAI CX', 'none' );

insert into tbDeviceType(typeId, typename, note ) values ( 'type1', 'PHONE', 'none' );
insert into tbDeviceType(typeId, typename, note ) values ( 'type2', 'PHONE', 'none' );
insert into tbDeviceType(typeId, typename, note ) values ( 'type3', 'PHONE', 'none' );

insert into tbPerson(PersonId, DepartmentId, Name, Title) values ('person1', 'depart1', 'Nguyen Van A', 'Staff');
insert into tbPerson(PersonId, DepartmentId, Name, Title) values ('person2', 'depart2', 'Nguyen Van B', 'Staff');
insert into tbPerson(PersonId, DepartmentId, Name, Title) values ('person3', 'depart3', 'Nguyen Van C', 'Staff');

insert into tbDepartment(DepartmentId, DepartmentName) values ('depart1', 'Administrative');
insert into tbDepartment(DepartmentId, DepartmentName) values ('depart2', 'Administrative');
insert into tbDepartment(DepartmentId, DepartmentName) values ('depart3', 'Product');

insert into tbDeviceModel(modelId, UnitId, TypeId, Specification, Quantity, ModelName)
values ( 'model1', 'unit1', 'type1','none' , 10, 'I AINT KNOW');

insert into tbDeviceModel(modelId, UnitId, TypeId, Specification, Quantity, ModelName)
values ( 'model2', 'unit2', 'type3','none' , 10, 'I AINT KNOW');

insert into tbDeviceModel(modelId, UnitId, TypeId, Specification, Quantity, ModelName)
values ( 'model3', 'unit3', 'type3','none' , 10, 'I AINT KNOW');

insert into tbDeliveryNote(DeliveryId, DeviceId, DepartmentId, DeliveryDate) values ('deli1', 'device1', 'depart1', 2020-1-10)
insert into tbDeliveryNote(DeliveryId, DeviceId, DepartmentId, DeliveryDate) values ('deli2', 'device2', 'depart1', 2020-1-10)
insert into tbDeliveryNote(DeliveryId, DeviceId, DepartmentId, DeliveryDate) values ('deli3', 'device3', 'depart2', 2020-1-10)

INSERT INTO TBINVENTORY(InventoryId, DeviceId, UsableValue, InventoryDate) VALUES ('inven1', 'device1', 90, 2020-1-10)
INSERT INTO TBINVENTORY(InventoryId, DeviceId, UsableValue, InventoryDate) VALUES ('inven2', 'device1', 90, 2020-1-10)
INSERT INTO TBINVENTORY(InventoryId, DeviceId, UsableValue, InventoryDate) VALUES ('inven3', 'device2', 50, 2020-1-10)
select * from tbDeviceType
select * from tbDeviceModel
select * from tbDevice


insert into tbUnit (unitId, unitName, note ) values ( 'unit1', 'DIEN THOAI A', 'none' );
insert into tbUnit (unitId, unitName, note ) values ( 'unit2', 'DIEN THOAI B', 'none' );
insert into tbUnit (unitId, unitName, note ) values ( 'unit3', 'DIEN THOAI CX', 'none' );
insert into tbUnit (unitId, unitName, note ) values ( 'unit5', 'DIEN THOAI CX', 'none' );



insert into tbDeviceType(typeId, typename, note ) values ( 'type1', 'PHONE', 'none' );
insert into tbDeviceType(typeId, typename, note ) values ( 'type2', 'PHONE', 'none' );
insert into tbDeviceType(typeId, typename, note ) values ( 'type3', 'PHONE', 'none' );



insert into tbDeviceModel(modelId, UnitId, TypeId, Specification, Quantity, ModelName)
values ( 'model1', 'unit1', 'type1','none' , 10, 'I AINT KNOW');

insert into tbDeviceModel(modelId, UnitId, TypeId, Specification, Quantity, ModelName)
values ( 'model2', 'unit2', 'type3','none' , 10, 'I AINT KNOW');

insert into tbDeviceModel(modelId, UnitId, TypeId, Specification, Quantity, ModelName)
values ( 'model3', 'unit3', 'type3','none' , 10, 'I AINT KNOW');
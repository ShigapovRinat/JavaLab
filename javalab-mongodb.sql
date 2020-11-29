db.courses.insert(
    {
        title: "DB",
        studentsCount: 30,
        hours: 99,
        description: ["CRUD operation", "NF"],
        students: [
            {student: {
                name: "Student1",
                surname: "Student1",
                group: "11-803"
            },
            student: {
                name: "Rinat",
                surname: "Student2",
                group: "11-803"
            }}
        ]
    }
    );
db.courses.insert(
    {
        title: "JavaLab",
        studentsCount: 30,
        hours: 99
    }
    );
db.courses.deleteOne({_id: ObjectId("5fb18919d58c784ea2d28712")});
db.teacher.insert({
    name: "Marsel",
    surname: "Sidikov"
});
db.courses.update({_id: ObjectId("5fb1888bd58c784ea2d28710")}, {
    $set: {
        teacher: ObjectId("5fb18c89d58c784ea2d28719")
    }
});
- SELECT s, e FROM Student s LEFT JOIN s.enrollments e
Breakdown of the Query
1. 'SELECT s,e'
- This mean that the query will select both 'Student'('s') and 'Enrollment'('e') entities
the result set will include pairs of 'Student' and their corresponding 'Enrollment' entities
2. 'FROM Student s'
- The Query start with the 'student' entity, which is aliased as 's'.
3. 'LEFT JOIN s.enrollment e'
- The 'LEFT JOIN' is used to join the 'Student' entity('s') with its 'enrollments' collection.
- The 's.enrollment' represents the association between 'Student' and 'Enrollment' this assumes thats
'Student' has a collection of 'Enrollment' entities(e.g, a 'OneToMany')
- Tthe alias 'e' is used to refer to the 'Enrollment' entities
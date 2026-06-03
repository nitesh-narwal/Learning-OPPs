package me.niteshh.OPPs.tutorial.collectionFramework.lecturePractice.List_Lecture;

public class Student implements Comparable<Student> {
        private String name;
        private double gpa;

        public Student(String name, double gpa) {
            this.name = name;
            this.gpa = gpa;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getGpa() {
            return gpa;
        }

        public void setGpa(double gpa) {
            this.gpa = gpa;
        }

    // let we have to compare 4 and 3 for that "4.compareTo(3)"
    @Override
    public int compareTo(Student o) {
            return Double.compare(o.getGpa(), this.getGpa());

            // return o.getGpa() - this.getGpa();
        //return o.getGpa() - this.getGpa() > 0 ? 1 : (o.getGpa() - this.getGpa() < 0 ? -1 : 0);
    }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", gpa=" + gpa +
                    '}';
        }
}



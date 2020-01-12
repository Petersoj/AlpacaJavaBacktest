var unique = require('uniq');

var data: number[] = [1, 2, 2, 3, 4, 5, 5, 5, 6];

console.log(unique(data));

export class Dog {
    private name: string;

    constructor(name: string) {
        this.name = name;
    }

    getName(): string {
        return this.name;
    }
}

alert(new Dog("asdf").getName() + " " +  unique(data));

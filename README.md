# Compose-VerticalThread
A layout composable that places its children in a form of a "thread" like reddit's threads.  
## Demo
https://user-images.githubusercontent.com/26522145/155023415-6c1bfcd1-8e92-43d4-85a0-5f6db1237778.mp4
## Usage
```kotlin
VerticalThread(
        items = /*my items*/,
        itemPadding = /*the padding of each item*/,
        decoration = { item ->
            // you can customize the decoration for every item
        }
    ) { item ->
       // your item content
    }
```
See: [Demo.kt](https://github.com/mr3y-the-programmer/compose-VerticalThread/blob/main/sample/src/main/java/com/mr3y/compose_verticalthread/Demo.kt) 
## License
```
MIT License

Copyright (c) [2022] [MR3Y]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:


https://user-images.githubusercontent.com/26522145/155023354-e06580a8-f0ca-476b-8bb0-d8a7729c8646.mp4


The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

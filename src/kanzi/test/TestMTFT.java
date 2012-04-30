/*
Copyright 2011, 2012 Frederic Langlet
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
you may obtain a copy of the License at

                http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package kanzi.test;

import java.util.Random;
import kanzi.transform.MTFT;


public class TestMTFT
{
    public static void main(String[] args)
    {
        System.out.println("TestMTFT");

        // Behavior Test
        {
            System.out.println("\nCorrectness test");

            for (int ii=0; ii<20; ii++)
            {
                byte[] input;
                
                if (ii == 2)
                {
                    input = new byte[] { 
127, -1, 0, 0, 0, -1, 0, 4, 0, 0, 0, 0, 0, 0, -2, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 2, 0, 0, 0, -5, 2, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, 3, 0, 1, 0, 1, -4, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 127, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, -2, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 1, -1, 0, 0, -1, 0, -2, 0, 0, 0, 0, 0, 0, 4, 1, 0, 0, 0, 0, 3, 0, 1, 0, 0, 1, 0, 0, 1, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 127, 0, 0, 0, -1, 0, 0, 0, 1, -1, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 127, 0, -1, 0, 0, 3, 0, 4, 0, 3, 0, -1, 5, 0, -4, 0, 4, 0, 0, 1, -1, -1, 9, 0, 0, 
0, 0, 0, 2, -1, 0, 0, 0, -2, 4, 0, 127, 1, -1, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, -1, 0, 1, 0, -1, 0, 0, 0, 23, 0, 4, 1, 0, 2, 4, 0, 2, 0, 0, 0, -1, 3, 1, 7, -4, -3, 5, 4, 5, 1, -1, 0, 1, -2, -6, 1, 2, 0, 1, 7, 4, 13, 127, 0, 127, 0, 2, 3, 2, 127, 0, 0, 0, 127, 0, -13, 0, 0, 5, 0, 0, -2, -3, 0, 0, 127, 0, 127, 1, 127, -12, -1, 3, 2, 0, 2, -3, -1, 0, 2, 3, 2, 127, -3, 1, 6, 0, 127, 4, -3, 8, -2, -1, 0, 8, 2, 2, 11, -1, 0, 1, -3, -1, 0, 0, 7, 1, 1, 0, -2, 127, -3, -7, 127, -3, 4, -4, -10, -1, 6, -32, 0, -1, 0, 0, 2, 127, 0, -4, 4, -5, 1, 0, -1, 1, 127, 127, 127, 0, 1, 127, 2, -2, 1, 1, 1, 1, 4, -4, 6, 1, 3, 4, 1, 127, 46, 24, -5, 6, -1, 3, -1, 
0, -1, 1, -1, 0, -3, 0, 1, 2, 2, -11, -1, -2, -1, -2, 0, -2, 0, 0, 4, 127, 1, 2, 0, -1, 1, 1, 0, -3, 0, -1, 1, -1, 1, -2, 0, 127, 0, 2, -12, -5, 1, 1, -2, -2, -3, 0, -1, 0, 0, 0, 1, 0, 3, 0, 4, -3, 1, -1, 0, -2, -5, 2, 2, 0, -3, -1, 1, 0, -1, 1, 127, 0, 127, 2, -6, 6, 2, 3, 0, -4, 1, 0, 0, 2, 22, 1, 1, 1, 0, 1, 127, 0, 2, 1, 1, 0, -3, 5, 127, 127, 1, 0, -2, -1, 2, 0, 127, 1, 2, -1, -2, -6, 3, -1, 127, -1, -1, -2, 1, -1, -1, 127, 127, 127, 1, -1, 127, -1, 127, 9, 1, 1, 2, -1, 0, -1, 127, 127, -6, -9, -5, -1, 0, 6, 1, 1, 0, 0, 127, 19, 0, 127, 0, 1, 127, 127, 1, 127, 1, 127, 0, 5, 0, -5, 1, 127, 127, 127, 2, -2, -2, -2, 1, -1, -7, 0, 3, 0, -2, -2, 1, -1, 1, -18, -1, 2, -1, 127, 2, -1, -2, 1, 127, 1, -2, -1, 127, -1, 4, 
-1, -1, 1, 1, -2, 127, -5, -6, -6, -5, -5, -5, 127, -3, 1, -2, 127, -4, 127, 127, 127, 127, -3, -2, 127, -5, -3, -3, 127, -4, 14, -1, -4, -2, 1, 127, 127, -1, -2, 127, -3, 127, 6, -3, 127, -2, -19, -1, 1, -1, 0, -8, 127, 8, -1, 127, 127, 127, 127, -1, -1, 0, -4, 1, 127, -1, -4, -1, -1, -7, 127, -5, -1, 1, -2, -1, -1, 127, -1, 127, 1, 3, 0, -1, -2, -9, -1, 127, 1, 0, -4, -4, 127, -2, -1, -3, -1, 127, 1, 127, -1, 127, -1, -1, -4, 3, -2, 127, -3, -1, -1, 1, 0, 0, 1, -6, 127, 127, -2, -1, 127, 127, 2, -1, 127, 1, 127, 2, 1, 127, -2, 127, 1, 0, -1, -1, 127, 2, -2, 0, -1, 0, 0, -7, -1, 127, 2, -1, 1, -3, -1, 127, 0, 3, -1, -4, 127, 1, -1, -4, 2, -2, -2, -1, -4, -7, -2, -10, 0, 4, 2, -1, -5, 14, 127, -5, -1, -8, -6, -7, -5, -9, 127, -3, 2, 127, -2, 5, -8, 1, -3, 127, 1, 1, 127, -1, 6, 1, 2, -2, 
2, 3, 1, 127, 127, 127, 127, -1, -2, 2, -1, 1, -7, -5, -1, -1, 1, -3, -1, 127, -1, -2, -4, 127, 1, -1, -8, 2, -5, 0, -7, -2, 127, 3, 1, -3, -2, -5, -2, 1, 1, 1, -1, 127, -1, 127, -5, -4, -1, 1, -7, -3, 127, 127, 1, 2, 127, -1, 127, 127, -2, -4, 127, 127, 127, 127, 127, -2, 127, -1, 6, 0, -1, 127, -2, -1, 1, -2, -4, 127, -2, -1, 0, -8, 2, -1, -7, 1, -5, -1, 127, -7, 127, 1, 1, 127, -4, -1, -1, -2, -2, -1, 0, -1, 1, 1, 127, 127, -1, 1, 5, -4, -6, -4, 1, -5, -6, -6, -6, -6, -6, -6, -5, -6, -2, -1, 2, 1, -2, 127, -5, 127, 127, 1, -1, 127, 1, -6, -4, -3, 1, 127, -1, 1, 1, 127, 1, 1, -2, 127, 127, -1, -2, 5, 1, -2, -3, -1, 127, -1, -5, -1, 1, 1, 1, -6, -6, -6, -1, -3, -1, 127, -1, 127, 5, 127, -2, -1, -6, -1, -1, -5, -1, 127, 1, 3, 2, 127, 1, -1, -1, 1, -1, -5, 2, 127, 3, 127, 127, -1, 
1, -9, 127, -9, -9, -1, -9, -2, -9, -9, -9, 0, -1, -3, 1, 127, -6, 1, -1, 127, 1, -10, 1, -1, -1, -2, -1, 127, -2, 127, 3, -2, -2, 127, 127, 127, 127, -1, 2, 1, -1, 1, -4, -1, 1, 127, -2, -1, 0, 1, -1, 3, 127, -6, -2, -3, -1, 1, -2, -3, 127, -1, -5, -1, -2, 1, 127, 1, -3, -1, -1, -4, 7, 2, 127, -1, -1, 1, -1, -1, -1, 1, -1, -3, -6, 2, -3, -3, -1, 127, 2, -1, -1, 0, 1, -2, -1, 1, -1, 127, -1, -1, -1, -6, 1, 1, -1, -2, 127, -2, -1, -2, -9, -2, -1, -2, -2, -4, -1, -2, 127, -1, 0, -2, -2, 4, 127, -1, 127, -4, 2, -1, -2, -3, -2, 1, 127, -1, 127, 127, 1, 127, -3, 127, 1, -4, -5, 127, 127, -4, -1, 127, -1, -2, 127, 3, 2, 127, -1, 127, -1, -1, -1, -1, -1, -1, 1, 127, 127, -2, 127, 0, 9, 24, 0, -5, 1, 127, 127, 127, -2, -3, 127, -1, -2, 1, 0, 127, -3, 127, 1, -1, 127, -2, -1, 3, 127, 127, 127, 127, 
-1, 127, -1, -1, -3, 127, 127, 127, 127, 127, 1, 127, 127, 127, -1, 127, 127, 127, 127, 127, -1, -1, 127, 0, 127, 127, 0, 1, -26, 127, 5, 127, 127, 127, 127, 127, 2, -3, 0, -1, -16, 127, 1, 1, 1, 0, -2, 127, 127, 127, 127, -1, -1, 127, 127, -2, 127, -2, 3, 127, 127, -3, -4, 127, -1, 1, -1, 127, -1, 1, 127, 2, -1, -1, -2, -9, 127, -8, -1, 127, 127, -1, 127, -2, 3, 0, -1, 1, -5, -4, 2, 127, 5, 127, 2, 127, 2, -5, 4, -5, 127, -1, 127, 1, 127, -2, -2, 127, 127, 0, 127, 127, 127, 127, 127, 127, 127, -2, 127, 127, 127, -1, -3, -1, 127, -2, 127, 127, 1, 1, -1, 3, 127, 1, -3, 127, 1, 127, 127, 2, -1, -12, 127, 127, 127, 127, 127, -1, 1, -9, -6, 127, 127, 127, 127, 1, 1, -2, 127, 127, -1, 5, 2, 1, 127, 127, 127, 127, 127, 127, 1, 127, 127, 1, 0, 1, 1, 127, 127, 127, 127, 127, 127, -5, -3, 1, 5, 127, -1, -1, 5, 1, -2, 1, -1, 127, 127, -5, -2, -5, 
-1, -2, 127, 127, 127, 127, 127, -9, -4, 1, 127, 127, 0, 127, -1, 127, 2, -5, -5, -8, 127, -8, 127, -3, -1, 127, -2, 1, 1, 127, 127, 1, -3, -2, -2, 127, 0, 0, -3, 127, 0, -4, 4, 127, 127, -5, 2, 127, 127, -3, 127, 1, -1, -1, 127, 127, 127, 127, 0, 127, -2, 1, -3, 1, 127, 1, -2, 1, -1, 127, 127, -6, 1, 127, -3, 127, -1, 127, 127, 127, 127, 127, -1, -1, 127, 1, 127, 127, 127, 127, 127, 127, -6, 127, 127, 127, 6, 1, 1, 127, -2, -1, -1, 1, 127, 127, 127, 127, 127, 127, -1, 127, -1, 127, 127, 127, -2, -1, -4, 127, -1, -1, -2, 127, -1, 1, 6, 127, 1, 127, 127, -1, 127, 127, 127, 127, -5, 127, 1, 127, 5, 5, 0, 10, -1, -1, 127, 4, 1, -1, 1, 127, -1, 127, 127, 127, 0, -4, -3, 0, -2, -2, 1, 127, 127, 127, -1, -2, -3, 127, -5, 1, 127, 2, 127, -2, 127, -2, 127, 127, -5, 1, -1, -2, 127, -1, -1, 0, -2, -1, 0, 4, 127, 0, -3, -12, 5, 127, 3, 2, 
-14, 127, 0, -2, 0, 127, 2, 3, -4, -8, -3, 1, 0, 127, -2, 127, -2, 2, 127, -1, 2, 16, -2, -1, -1, 0, 0, -8, 127, -1, 3, -3, 3, 127, -4, 1, 127, 127, -4, -1, -1, 0, -3, 0, 5, -1, -2, 4, 2, 127, -2, -1, 5, -8, 2, -4, -1, 4, -1, -2, 127, 127, -5, 3, 127, 127, -4, -10, -3, -4, -5, 3, -5, -8, -5, -2, 127, 1, 0, 127, 1, 127, -1, 127, -4, -4, 0, 2, 2, 2, 1, -5, -4, -23, -6, 127, 127, 2, -1, -3, 1, -1, -2, 127, 4, 4, -2, -4, 127, -2, -11, -1, 1, -8, -19, 127, -2, 12, -1, 5, -1, -2, -1, -4, 127, -3, -2, 2, -1, -1, -14, -1, -5, 6, -1, -1, -3, -3, -2, -5, 2, 1, -3, 127, 1, -5, -2, 3, -1, -10, -3, -2, -2, -3, 4, 127, -4, 127, 2, -6, -3, -1, -3, -1, -2, -2, -3, -1, 127, -5, -3, -2, -6, -1, -1, -1, 2, -4, 127, 2, -2, -3, -6, 1, 3, 1, -2, -2, -5, 127, -1, 2, -4, -2, -2, -2, 127, -1, 4, -1, 
-2, -1, -30, 9, 9, -3, 15, 8, -10, 2, 3, 127, 1, 23, 127, -1, 1, 2, 0, -2, 0, -2, 2, 127, 127, -1, 2, 2, -1, -1, -3, -2, -2, -3, -1, -1, -1, 14, -7, -2, 127, 2, 127, -1, -1, 127, 3, 127, -1, 1, 1, 127, 1, 127, -1, 1, -1, 127, -2, 0, 127, 1, -10, 2, 127, -1, -3, -3, 4, 1, 1, 0, 127, 0, -1, -1, -1, -1, -1, -4, -2, -10, 127, -1, 127, 127, -1, -1, -1, -2, 127, 2, -1, -1, 1, -1, 127, -11, -8, -12, -1, 2, 0, 0, 127, 127, 1, 127, 127, 1, -21, 127, 127, 127, 127, -5, -1, 1, 3, 1, 3, -1, -1, 2, 1, -1, -10, -3, 127, -2, 1, -1, -1, 1, -2, -1, -2, 127, 1, 127, 127, -2, 1, 127, -4, -1, -3, 1, -3, 127, -3, -1, -1, -1, 3, -1, 127, -7, -1, -14, -2, -5, -2, -2, -1, 127, -1, -1, -1, 127, -2, 2, -3, -4, -2, -1, 127, 1, 127, -5, 127, -2, -5, 1, -2, -1, 0, -4, 127, -1, -1, -6, -1, 127, 1, 2, -5, -2, -1, 5, 
-3, -3, 0, 1, -2, -2, 0, -3, -1, 1, 1, 0, 1, 1, 15, 5, -7, 5, 0, 127, 0, 1, 5, -2, -1, 1, 0, -4, -2, 127, -1, -1, 1, -6, 2, 127, 1, 1, 1, 127, 1, 2, 3, -3, -1, 6, -4, -1, -3, 1, 127, 2, 1, 2, -5, -3, 0, -6, 4, 4, 4, 2, -11, -1, -5, 1, -17, 10, 0, 8, -1, 0, 10, 127, -2, 4, 0, -4, 19, 127, 127, -1, 2, -4, 8, 15, 127, -2, -1, -1, -1, 127, -5, -2, -7, -6, -5, 1, -13, -6, -5, 0, -4, -1, -19, -1, 127, 7, 127, -5, -1, 0, 1, -1, 1, 127, 5, 16, -3, -2, 0, 127, 2, -8, 127, 2, 8, 127, -2, -2, -1, 1, -3, 127, 127, 1, 127, -2, -4, 4, 0, -1, 5, -6, -2, -1, -7, 11, 127, -2, 3, -4, 127, 4, -1, -2, -1, 1, -1, 1, -1, -1, 127, 127, -7, 127, -14, 127, -4, 4, -2, 127, -6, 127, 2, 0, -2, 127, 127, 2, -1, 0, 127, 3, -8, 127, 3, 1, 127, 1, -2, 12, 1, 127, 0, -1, 4, 127, -1, 127, 
-10, 1, -1, -2, 127, 127, 3, -3, -3, -4, -1, -14, -1, 0, -1, 127, -5, 4, -4, 20, -3, 3, 2, 2, 2, 8, -9, -2, -2, 1, -1, -6, 127, 127, -1, -14, -4, 0, 10, 0, -1, 1, 0, -3, -2, 3, -9, -1, 1, -2, -6, 3, 1, -5, -1, -3, -9, 0, 127, 0, 3, 127, 2, -2, -1, 10, -6, -25, -22, 1, -1, 1, 0, 7, 0, 127, -5, -1, -3, -1, -5, -1, 1, -2, 127, 127, 127, -1, -22, 6, 0, 127, 2, 4, 1, -3, -1, -1, 0, -1, 3, 2, 1, 2, 1, 1, 27, -7, -9, -7, -1, -1, -4, -1, 2, -1, -2, 1, 1, 1, -2, -1, -1, 127, 2, 127, 5, 2, 0, -2, -2, -10, -8, -4, -1, 1, 2, -3, 4, 127, -12, -2, 127, 1, 3, -2, 1, 127, 127, -1, 3, -1, -2, -4, -1, 1, -2, 2, 127, -1, -2, 2, -3, -1, 0, 0, -7, 127, 3, -4, -8, -1, 1, -1, 0, 127, -12, -2, -1, -3, -3, -22, 1, 1, 7, 0, -1, 3, 0, 0, -5, 2, -1, -5, -6, -13, -12, 1, -43, 11, 
2, -5, 127, -1, -2, 2, -1, -9, 1, 2, 1, -2, -4, -1, -8, -73, 9, 5, -1, 2, -3, 127, -1, 17, -1, -1, 7, 13, 1, -4, 127, 5, 127, -5, -2, -4, 1, 1, 2, 1, 0, -5, -2, 1, 127, -1, 127, 1, 1, 0, -5, 1, 1, -1, 127, -1, -3, -5, 127, 127, 127, 2, -1, -2, 4, -2, -2, 2, -21, -1, 2, 0, 1, 2, -2, -17, 3, -2, -5, 9, -1, -2, 1, -3, 1, -25, -1, -1, -25, 5, -13, 0, 0, -4, 1, -1, -4, -10, -23, 1, 1, 127, -2, 22, -1, -1, 127, 4, -3, -6, -1, 3, 9, -2, -2, 127, -1, 1, -4, 34, 127, -4, 4, -2, 0, 127, -5, -4, -3, -5, -3, -1, 6, -2, -2, -1, -1, 1, 1, 1, 3, 4, -5, 22, 8, -5, 5, 5, -2, -70, 19, -16, 6, 2, -1, 5, -4, 20, -2, -20, -23, 127, -1, 127, -3, 4, 1, -7, -2, 34, -1, -3, -1, -14, -1, 12, -5, -1, -2, 3, -15, 12, -1, -2, 0, 18, 5, -18, -1, -85, 0, -6, -2, -10, 6, 0, -8, 127, 3, -1, 
-5, 1, 2, 18, 2, -1, -6, -3, 13, -7, 3, -1, -9, 7, 9, -5, -18, -3, 3, -6, -10, -2, -20, -16, -20, 21, 5, 4, 2, -2, 0, 3, -2, -2, -6, -4, -9, -7, 127, 1, 2, 127, -3, -10, -13, -23, 6, 0, -11, -3, 8, 3, -4, 9, -12, -1, -2, -12, 5, 8, -2, -15, -1, 19, -1, -2, 8, -1, 2, -1, 6, 15, 0, -1, -19, 3, -18, -7, -4, -9, 29, 1, -5, -20, -25, 3, -5, -14, 13, 1, -12, -2, -6, 18, -4, 12, -3, 9, 6, 5, -5, -2, -3, 28, -6, -1, 1, 12, -14, 3, -1, 9, -65, 20, 0, -17, -16, 10, -12, -7, -12, 9, -4, -4, 4, -23, -8, -5, 9, -3, -29, -2, -22, 9, -24, 0, -31, 4, -21, 24, -5, -23, -22, 7, -21, -1, 4, 7, 19, -23, -27, -17, -4, 7, 3, -59, 0, -1, -23, 22, -27, 7, 31, 2, 20, -16, -34, 3, -27, 9, 4, 4, 43, -17, 99, -22, -66, 15, -12, -59, 28, -39, -22, -42, 29, 23, 127, 127, -1, 127, -1, 127, 1, 127, 127, 127, 1, 1, 127, -4, 
1, 1, 127, 1, 127, 2, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 0, 1, 1, 127, 127, 127, 127, 127, 2, 4, 0, 127, -1, -1, -4, 127, 127, 2, 127, -1, 127, 127, 3, 127, 127, 127, 127, 127, 127, 127, 2, -1, 127, 127, 1, 127, -1, 127, -1, -1, 1, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 3, 1, 1, 127, 127, -1, 1, 127, -5, 127, 0, 127, -1, 1, 127, 127, 127, 127, -1, 127, 1, -2, -1, 127, 127, 127, 127, 127, 3, 127, 127, 1, 127, 127, 127, 127, 127, 1, -4, 127, 127, 127, 127, -1, -6, -6, 127, 127, -1, 127, 3, 127, 127, 127, 127, 0, 127, -5, 127, 127, -1, 127, 127, 1, 127, 2, 1, 127, 1, 127, -8, 127, -1, 1, 1, 127, -5, 127, 1, 127, 127, 127, 1, 127, 127, 127, 127, 1, 4, -17, 1, 127, 1, 127, 127, 2, 127, 127, 127, 127, 1, 127, 1, -1, -1, 127, -3, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, -1, 127, 127, 127, 
7, 127, 127, 127, 127, -1, 127, 1, 127, 1, 127, -1, 127, 1, 127, 2, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, -5, 127, 127, -2, 2, 127, 127, 127, 127, 127, -1, 127, 127, -1, 127, 127, 127, 127, -1, 127, 1, -1, 1, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, -1, 127, 127, 1, 127, 1, 127, 127, 127, 127, 127, 127, -4, 1, -1, 2, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 1, 127, -2, 127, 127, 1, 127, 1, 127, 1, 127, -1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, -1, 1, 127, 127, 127, 127, -1, 0, 1, -1, 1, 127, 127, 127, 127, 127, 1, 127, 127, 1, 127, 127, 127, -2, 127, -4, -3, -9, 127, -2, -1, 127, 127, -1, 127, 127, 1, -1, 127, 127, 127, 2, 127, 127, 3, -1, 127, -1, 127, -1, 127, -1, 127, 127, -4, 1, 127, -5, 127, -1, 127, 127, 127, -6, 127, -1, -2, 127, 1, 127, 127, 2, 127, 127, 127, 127, 
-1, 127, 127, -1, 127, 1, 127, 127, -1, 127, 1, 127, 127, 127, 127, -2, 127, 127, -6, 127, 1, 0, -1, 127, 127, -1, -1, 127, 127, 1, 127, 2, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 3, -2, 127, 127, -4, 127, 127, 127, 1, 127, -1, 127, 127, 127, 127, -5, -2, -2, -3, 127, 127, 5, -1, -1, 127, 127, -3, -3, -3, -3, -2, 127, 0, 127, 127, 2, 127, -1, -3, 127, -4, -2, -3, -2, 127, 127, 127, 127, 127, 1, 127, -2, -6, 127, -7, 127, 127, 1, -1, 1, -3, 4, 6, 127, 127, 127, 127, 127, 127, 127, -1, 127, -1, 127, 127, 1, -3, 127, 1, -1, 127, 127, 0, 127, 127, 127, 127, 2, 127, 127, 127, 1, 127, -1, -1, 1, 127, 127, 1, 127, 2, 3, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, -1, 127, -1, 127, 127, -9, 127, -2, 127, 2, 127, 127, 127, 127, 127, 127, -1, 127, 127, 1, 127, 127, 1, 127, 127, 127, 127, 127, -1, 127, 1, 127, 127, 127, 127, 127, 127, 127, -1, 1, 1, 127, 127, -2, -1, 127, 
-1, 127, 127, -1, 127, 1, 127, 127, 127, 127, -2, 127, 127, 1, 127, 127, 127, 2, -1, -1, 127, 127, 127, 127, -1, 127, 127, 1, 127, 127, 1, -2, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, -2, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 2, 1, 2, 127, 1, 127, 127, 127, -2, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, -2, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 1, 127, -3, 127, 127, 127, 127, 127, 127, 1, -1, 1, 127, 127, 127, 127, 1, 127, 127, 127, 1, 127, 127, 127, 1, 127, 1, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, -1, 127, 1, 127, 127, 127, 1, 127, 127, 127, 127, -1, 1, 127, -1, 127, -1, 127, 127, 127, 127, -2, 127, 127, 127, 127, 127, 0, 127, 127, 127, -12, 1, 127, 127, 127, 1, 127, 127, 
127, 127, 2, 127, -1, 1, 127, 1, 4, -1, 127, 0, 2, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, -1, -1, 127, 127, 1, 2, 127, 0, 127, 127, 127, 127, 0, 127, 1, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, -1, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 1, -2, 127, 127, 1, 127, 1, -1, 127, 127, 1, 127, 127, 127, -3, 127, 127, 127, 127, 127, 127, 127, 127, 127, 2, 127, 127, 127, 127, 127, 127, 1, 4, -1, 127, 1, 127, 127, 127, 127, 1, -2, 127, 127, 127, 127, -1, 127, 127, 127, 127, 1, 1, 1, 1, 127, 127, 1, 1, 127, 127, 2, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 1, 1, 127, 127, 127, -2, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 2, 127, 127, 1, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 127, 1, 127, 127, 7, 127, 127, -2, 127, 127, 1, 127, 127, 
127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 127, 127, 127, 1, -1, 1, 127, -2, 127, -1, 127, 127, 127, 127, 127, 1, -2, -1, -3, 127, 127, 127, 127, 1, 127, 127, -1, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, -1, 127, 127, 127, 1, -9, 127, 127, 127, 1, 127, 127, 127, 127, 1, 4, 127, 2, 127, 127, 0, 127, 127, 127, 127, 1, 127, 127, 127, 2, 127, 1, 127, 127, 1, 127, -1, 127, 127, 4, 127, 1, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 2, 1, 127, 127, 127, 127, -3, 1, 127, 2, 127, -1, 127, 127, 127, 127, 127, 127, 127, 127, 2, 1, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 1, 127, 127, 127, 127, 1, 127, 1, 1, 1, 1, -3, 1, 127, -1, 127, -2, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 1, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 1, 127, 127, 5, 127, 
127, 127, 127, 127, 1, -1, -2, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 127, 127, -1, 1, 127, -3, -2, -3, 127, 127, 5, 127, 127, 127, -3, 127, 127, -1, 127, 127, 127, 127, 127, 1, 127, 127, 2, 127, 127, -1, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 1, 127, -1, 5, 127, 127, 3, 127, 1, 127, 127, 3, 127, -1, 127, 127, 1, 1, 127, -1, 1, 1, 127, 127, -1, -3, 127, 1, 127, -1, 127, -5, 127, 127, 127, 127, 127, -1, 127, 127, 1, 127, 127, 1, 127, -2, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, 2, 1, 127, 127, 127, 127, 127, 127, 0, 127, 2, 127, 127, 127, 127, 127, -1, 127, 127, 127, -1, -1, 127, 127, 127, 127, 127, 4, 127, 127, -1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, -1, 1, 127, 127, -6, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, -3, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 
127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 2, 127, 127, 127, 2, 127, 1, 127, 127, 127, 127, -1, 127, 127, 127, 1, 127, 2, 127, 1, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 5, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -7, 127, 127, 127, 1, 127, 1, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, -3, 127, 127, 1, 127, -2, 1, 127, 127, 0, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 1, 127, 127, -3, 127, 127, 0, 127, 127, 127, 127, -1, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 5, 127, 3, 127, 127, 127, 1, 1, 1, 1, 127, 3, 127, 5, 127, 127, 127, 1, 127, 0, 127, 2, 127, 127, 127, 3, 127, 1, 127, 127, 1, 127, 1, 127, 127, 127, 127, -1, 1, 1, 1, 127, 127, -3, 127, 127, 127, 127, 127, 
127, 1, -1, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, -1, -1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 0, 127, 127, 127, 127, -1, 127, 1, 127, 127, 127, 1, 127, 127, 127, 127, 127, -6, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 2, 127, 127, 127, 1, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -3, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 0, 0, 127, 127, 127, 127, 1, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, -7, -1, 127, 127, 127, 127, 127, 127, 4, 127, 
127, 127, 127, 127, 127, 127, 127, 127, 127, 1, -1, 127, 127, 127, 127, 127, 0, 127, 127, 127, -1, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 2, 127, 1, 127, 127, 127, 2, 127, 2, 127, 127, 127, -3, 127, 127, 127, 1, 127, 127, 127, 127, 127, 3, 127, 127, 127, 127, 127, 127, 3, 127, 1, 127, 1, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 2, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, -1, 1, 127, 127, -1, 127, -3, 1, 127, 127, -1, 127, -1, 127, 127, 127, 127, 127, 1, 127, 2, 127, 2, 127, 127, 127, 2, 127, 127, 127, 127, 127, -1, 127, 127, 127, 1, 1, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 4, 127, -3, 127, 127, 127, 127, 127, 127, 127, 127, 127, 
127, 127, 127, 1, 1, 127, -1, 6, 127, 127, 127, -1, 127, 2, 127, 127, 1, 127, 127, 127, 127, 127, 1, -3, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -2, -3, 127, 127, 127, 127, 127, -1, 1, 1, 127, 1, 127, 127, 2, 127, -1, 127, 127, 3, 127, 127, -1, 1, 127, 127, 5, 127, 1, 127, 2, 127, 127, 1, 5, 127, 127, -1, -1, 127, 127, 127, 127, -1, 127, 5, -1, 127, 127, -1, 2, 127, 127, -3, 127, -1, 127, 127, 127, 127, -1, 127, 127, 127, 1, 127, 127, 127, 127, 127, 1, 127, 127, 127, 0, 127, 127, 127, 127, 127, 127, -1, 3, 1, 127, -1, 127, 127, 127, 1, 1, 1, 127, 127, -1, 127, 127, 3, 2, 1, 127, -1, 127, 1, 127, 127, 127, 127, 127, 1, 1, 127, 127, -2, 127, 1, 127, 127, 127, 127, 127, 127, 2, 127, 127, 127, 127, 127, 127, -1, 127, 1, 127, 127, -1, 1, 127, 127, 127, 127, 127, -2, 127, 127, 127, 127, 1, -1, 127, 1, 4, 2, 127, 127, 127, 0, 127, 127, 127, -2, 127, 127, 127, 1, 127, 1, 
127, 127, 127, 127, -4, 127, 127, -2, 4, 4, 0, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, -1, -2, 1, 127, 127, 127, 127, 127, 2, 127, 127, 127, 127, 127, 127, 1, 1, 127, 127, 127, 127, 127, 127, 127, -1, 127, 1, -1, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 1, 127, -1, -1, 1, -1, 127, 127, 127, 1, 2, -1, 127, 127, 127, 127, 127, 1, 127, 127, -1, 127, 1, -1, 127, 127, 127, 3, -1, 127, -1, 127, 127, 127, 127, 1, -4, 3, 127, 2, 127, -1, 127, 127, -1, 127, 1, 127, 127, 127, -1, 2, 127, 1, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 1, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, -3, -1, 1, 127, 127, 1, 127, -1, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 2, 127, -1, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 1, 127, 127, 127, 127, 127, -1, 127, 
127, -1, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 1, 1, -1, 127, 127, 127, 3, 127, 1, 127, 127, -1, 0, 127, 127, 127, 127, 2, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 2, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 127, -1, 2, 127, 127, -2, 127, -1, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, -1, 127, 127, 127, 2, 127, -1, 127, 127, 127, 127, 2, 127, 127, 127, -1, 127, 127, 127, 127, -1, -1, 127, 3, 127, 127, 127, 127, 127, 127, 127, 1, 127, -2, 127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 1, 127, 127, 1, 1, 1, 6, 127, 127, 127, 127, 127, 127, 1, 7, 127, 127, -2, 127, -1, 127, 127, 127, 1, 127, 1, -1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, -1, 127, 127, 127, 127, 127, 1, 2, 127, -2, 127, 127, -1, 3, 127, 127, 1, 127, 127, 127, 127, 1, 127, 1, 127, 127, 127, 127, 127, 127, 
1, 1, 127, -1, 1, 127, 127, 127, 127, 127, 127, 0, 4, 1, 2, 127, 127, 127, 127, 127, -2, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, -3, 127, 127, -3, -1, 1, 127, 127, 127, 127, -2, 1, 127, 1, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 1, 1, 127, 1, 1, 127, 127, 127, -2, 1, 127, 1, 127, 127, 127, 127, 127, 1, 127, -1, 2, -1, 127, 127, -1, 4, 127, 127, 0, 127, 127, 127, -2, 127, 127, 1, 127, 127, 127, 127, 1, 127, 127, -1, -1, -6, 127, 127, -2, -2, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 6, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -1, 1, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 1, 1, 127, -10, -1, 127, 2, 1, 1, 1, -1, 127, 127, 127, 127, 127, 1, 1, 127, 127, 5, 127, 127, -2, -1, 3, 127, 2, 1, 1, 127, 127, 127, 127, 6, -1, 127, 127, 
-1, 1, 127, 1, 127, 1, -5, 2, -3, 1, -3, 127, 1, 127, -1, -1, 2, 127, 127, 127, 127, 1, 127, 1, 127, 1, 127, 127, 127, 127, 127, 127, 127, -1, 127, -1, 127, 127, 127, 2, 127, 3, 1, -1, 1, 127, 127, -2, -4, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 2, 1, -2, 127, 2, 127, 127, 127, 127, 0, 127, 127, 127, 4, 127, 127, 127, 127, 127, 127, -6, 127, 127, 127, 127, 127, -3, 127, 127, 127, -1, -1, 127, 1, 127, 127, 127, -1, -1, 127, -3, 1, -2, 127, 1, 1, -5, -2, 127, -2, 2, -1, 127, 127, -1, 127, 127, 127, 127, 1, 1, 2, 1, 127, 127, 127, 127, 127, 127, 127, 2, 2, -1, 127, -2, 1, 127, 2, 127, 127, 1, -1, 127, 127, 127, 127, 2, 127, 127, 127, 127, -1, 1, 127, 1, -1, 127, 127, 127, 127, 127, 127, 127, 1, 1, 127, 127, -6, 127, 127, 2, 1, 0, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 127, 127, 1, -1, 127, 127, -1, 0, 
127, 127, 127, 127, 127, -1, -1, 127, 127, 1, 127, 127, 127, 127, 127, 127, 127, 127, -1, 127, 127, 127, 127, 127, 1, 127, 127, 127, 127, 1, 127, 127, 1, 127, 127, 127, 127, 127, 127, -1, 2, 127, 127, 0, 127, 127, 0, 1, 127, 127, 127, 1, 1, 127, 127, 127, 0, 1, 1, 127, 127, 127, -1, 127, 127, 1, 2, -2, -2, 127, -1, 127, 127, 127, 127, 1, 127, 127, -1, 7, 127, 127, 127, 3, -1, 1, 127, -1, -4, -1, 127, 1, -1, 127, -1, 127, -1, 127, 127, 1, 127, -1, 127, -3, -1, 127, 1, 1, -3, -1, 127, 2, 127, 127, 127, -1, 127, -1, -1, -2, -1, 127, 127, 127, 127, -1, 127, 127, 2, 127, 127, 127, 127, 1, 0, 1, 2, -1, -1, 1, 127, 127, 127, 1, 127, 127, 127, 1, -87, -77, 50, -75, -41, -125, -77, -32, -17, -106, -8, -1, 23, -84, -85, -25, -65, -14, 34, 34, 34, -6, 29, 0, 4, -20, 21, 6, -15, 11, 16, 6, -11, 5, 8, -4, 28, 4, -3, -3, -1, -83, -9, -3, 11, 10, 6, 10, -2, -5, -2, -2, 
6, 18, 41, 29, -5, -5, 0, 11, 36, 27, 4, 5, -11, 11, 25, 0, -3, 12, -6, 30, -6, 32, 29, 43, -60, 21, -17, -22, 127, 25, 4, 29, 45, -12, 41, 1, -17, 15, 3, 9, 6, -51, 26, 2, 30, 13, -5, -2, 127, 9, -6, 12, 23, 7, 8, -11, -1, -10, 4, -17, -5, 0, 3, 17, -5, 9, 0, 4, -4, 1, -9, 8, -7, -7, 1, -3, 3, -5, -3, -10, 6, 10, -31, 7, -2, -1, 3, -2, -7, 1, 9, 14, 3, -3, -3, 1, -44, 1, 3, -7, 5, 6, 18, -11, -4, 5, 7, -1, -2, -3, 4, 6, 3, -4, 5, 7, -9, -11, -1, 9, 7, 4, 127, 127, 127, 127, 127, 127, 127, 127, 2, 127, 127, -8, 6, 13, 1, -2, -5, -2, -3, -3, -6, -3, -3, -5, 12, 5, -1, -3, 6, -21, -2, 1, 127, -8, -4, 1, -1, -3, 3, -8, -9, 3, 6, 1, 127, 127, 127, 127, 10, 4, -6, -5, -6, -5, 3, 1, 0, -2, -3, 14, -10, 2, 2, 3, 0, 7, 1, 1, 6, -3, -2, -1, 127, 34, -1, -1, -11, 2, 
-3, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, 127, -10, 127, 127, 7, 127, 127, 127, 127, -11, -4, -3, 1, -4, 11, -8, 1, -3, 4, -8, -7, -9, 127, 5, 3, -3, -1, -2, 1, 127, 7, 14, 1, 3, 4, 1, 1, 8, 127, 8, 2, 7, 1, 4, 1, 15, -1, -5, -3, 127, 127, -3, 127, -4, -4, -1, 8, -7, -4, -4, -6, -5, -4, -2, 0, 2, 4, 0, -7, -7, 127, -7, 5, -8, -8, -8, -8, -8, -8, -8, -1, -8, -8, -8, 1, -8, -8, -1, 127, 1, -2, 5, -2, 1, 2, 2, -1, 1, 2, 1, 3, 0, 1, 1, -8, 22, 127, 4, 39, 1, -5, 1, -1, 1, -7, -8, 4, 1, 1, -8, 1, 127, 127, 22, 6, 7, 5, -1, -2, 3, -1, -8, 1, 2, -3, 127, -25, 6, 1, -2, -2, 3, -3, 3, 3, 2, 2, 1, -3, -10, -10, -9, -9, -9, 2, 5, 127, -7, -7, -10, 1, 3, 7, 5, -8, -8, 127, -10, -5, -8, 2, 9, 1, 2, 2, 5, 3, -8, -10, -9, -7, 3, 3, 1, 3, -1, 2, -2, 
1, 2, 1, 2, 0, 1, 2, -1, 2, 2, 2, 2, 2, 2, 3, 3, -1, 4, 1, -6, 1, 1, -1, 6, 8, 2, -3, 1, -2, -1, -2, 1, 2, 4, 1, 0, 5, -7, -4, 4, -2, 3, -5, -4, 2, 127, 7, -2, 1, -1, 127, 12, 5, -2, -2, 4, 2, 127, -5, 1, 127, -5, 1, -5, -8, 127, 127, -1, 2, 15, 1, 1, 1, 127, 127, 6, 127, 27, -5, 127, 127, -6, 127, -3, 4, 3, 6, 4, 2, -1, 7, -1, 0, 1, -4, 1, 2, 1, 5, -7, 3, -2, -4, 3, 1, 1, 1, 2, 1, 3, -3, 5, -2, 1, -5, -1, -12, -6, -1, 4, 2, 127, 3, -10, -10, -10, 1, -2, -2, 127, 2, 1, 1, 6, -4, 4, 2, 3, 4, 3, 127, 2, -5, -5, -4, -6, 2, 127, 127, 2, 1, 127, 2, 2, 2, -3, 1, -5, -2, 1, 0, 1, 3, 1, -4, -1, 2, 127, 1, 5, 0, 2, -3, 127, 6, 1, 127, 8, 0, 0, 127, 28, 4, 26, -5, 7, 21, -2, 11, -1, 1, 1, -1, 2, -8, 4, 3, 127, 8, 1, 
127, 127, -61, -1, 127, 127, 127, 127, 127, 127, 127, 127, -2, 1, 127, 127, 127, -1, 127, 0, -8, -6, 2, -16, -5, 5, 1, 127, -4, -5, 9, 0, 1, 10, 0, 1, 127, -3, -6, 1, 0, -2, 3, -1, -5, 2, 1, -1, 1, 4, 5, -1, -4, 1, -4, -1, 2, -1, 127, -23, -1, 3, -3, 127, 1, -2, 5, 127, -5, 1, 0, -5, 1, -3, 5, 127, 1, 127, 2, 3, 2, -1, 4, 3, 5, 1, 2, 7, 2, 127, 2, 2, 1, -4, 8, 1, 3, -3, -10, 5, -2, 127, 1, -2, 9, 2, 6, -1, -1, 2, 1, 1, 1, 1, 1, 3, 127, 0, 4, -1, 4, 127, -1, 1, 1, -13, 11, 1, -1, 7, 127, 4, 1, 1, 1, -4, 2, 127, 6, -2, 1, -2, 2, 127, 3, 0, 127, 1, 1, 9, 4, -1, 3, 127, 127, 1, 5, 3, 1, 127, -1, 127, 9, -4, 1, -9, 2, 1, 127, -6, 4, -9, 3, -2, -7, -6, -4, -7, 0, -5, 4, 8, 127, -10, 3, 1, -3, 1, -2, -10, 3, 9, 2, 127, -3, 1, 1, 2, 4, 127, 
3, 1, -5, 6, -6, -1, -1, 127, -2, 1, -14, 16, 27, 13, 1, 3, 1, 127, 127, 1, 127, 127, 127, 2, 14, 6, 2, -1, 127, 1, -1, 127, 1, -1, 0, 2, -2, 3, 5, 4, 2, 0, 4, -1, 1, 2, -1, 1, 1, 1, -1, 3, -3, 5, 5, 127, 1, 4, 127, 0, -2, -1, -2, 5, 127, 4, -2, -3, 1, 127, 5, 8, -7, 0, -4, -7, 4, 127, 2, -2, 0, -2, -1, -2, 0, -2, 5, 127, 1, -1, 0, -1, 1, 2, 2, -1, 1, 127, 2, -3, 1, -2, 127, 127, -8, 0, 0, 127, -1, 2, -1, -4, 1, 127, 127, 5, 2, 2, 2, -1, 1, -1, 2, 1, 0, 1, 2, -2, 2, 3, 0, -3, -4, 127, 127, 1, 1, 127, 4, 1, 1, 1, 1, 1, 127, 2, 2, 127, 2, -2, 3, 2, 2, 127, 127, 1, 127, -2, 1, -3, 1, 1, 5, 1, 2, 127, 1, -1, 127, 1, 1, 6, -3, 3, 127, 1, 0, 2, 127, 5, -1, -10, -2, 2, 0, 1, 3, -2, -1, -3, -4, 1, 2, 1, 127, 1, 1, 1, 127, 2, 
0, 6, -1, 127, 127, 0, -3, 0, 127, 0, -4, -3, 127, 2, -5, 0, 6, 2, 127, 0, -1, 2, -2, 127, 1, 1, 4, 0, 4, 1, 2, -5, -1, 127, -1, 2, -1, 127, 1, 1, 2, 127, 3, 127, 0, 4, 3, 127, 127, 6, 3, 127, -1, -1, 1, 1, 127, 2, 1, 127, 5, 1, 1, 3, -2, -2, -6, 1, -1, 1, 1, 2, -4, -5, 2, 1, 127, 1, 1, 127, -1, 1, 1, 1, 3, 127, 2, 2, 5, 1, 127, 1, 1, 3, -1, 0, 127, 5, -1, 1, 1, -2, 1, 0, -3, -1, 1, -13, 127, 11, 127, 1, 8, -21, 28, 3, -1, -4, -1, -1, 6, 1, 127, 3, 1, -1, 1, 1, -12, 4, -8, -7, -1, -5, 127, -4, 1, 1, 1, 1, -2, -4, 127, 0, 1, 127, 2, 1, 2, 3, 2, 2, -1, 2, 127, -2, 1, 7, 1, 2, 2, 1, 2, 4, 1, 127, 8, 9, -1, 127, -2, 4, 127, 5, -3, 1, 1, 127, -8, -4, -2, -5, 0, -1, 127, 1, -1, 7, -4, 3, 127, 6, -10, -2, 1, 1, -2, -1, 26, 0, 
-12, -3, -22, 31, -2, -3, 1, -5, 2, -3, -1, 127, 0, 127, 127, -1, 3, 2, 2, 127, -3, 5, 5, -3, 0, -2, 1, 127, -5, -1, 127, 127, -4, 127, -5, 3, 127, -2, 3, -2, 4, 14, 3, -3, -1, 1, 127, 127, -1, 6, 0, -5, 2, 2, -2, -1, -2, 1, 127, 127, 2, 127, 1, -1, -1, 1, 2, 1, 1, -1, 3, 1, 1, -3, 0, 127, 2, -4, -3, -1, -1, 6, 0, 0, -5, 4, 0, 0, -1, -5, -1, 0, 2, 0, -1, 127, -1, 127, 2, -2, 3, -1, 2, -1, 0, 0, 18, 1, 1, 1, 0, -1, 2, 127, 1, 2, -1, 4, -1, 127, 127, 6, -3, 127, 2, 2, 4, -3, -2, -1, 2, 127, 0, -1, -2, 0, 1, 127, 0, 2, 0, 3, 1, 4, 2, 1, -1, 127, 127, 2, -1, 127, 127, 1, -1, 1, 127, 1, -1, 2, 1, -3, 127, 127, 127, 1, -1, 127, 127, 5, 2, -1, 2, 1, 127, -1, 127, 12, 127, -2, 2, -2, 19, -4, 127, 127, 1, 4, 127, 2, 3, 127, 1, 127, 1, 1, 1, 2, 127, 1, 
127, 127, 1, 2, 1, 2, 3, -1, 2, 1, 127, 1, 127, 127, 1, 127, 4, 127, 1, 1, 3, 1, 2, 3, 127, 7, 127, 2, -1, 127, 1, 127, 1, 127, 127, 127, -1, 1, 3, 127, 1, 127, 127, 127, 1, 127, 127, 1, 127, 1, 2, 1, -1, -1, 1, 1, 1, 1, -3, -1, 4, 4, -1, 2, 127, 1, 127, 127, 127, 3, -3, -1, 2, -1, 2, 127, -1, 0, 13, 1, 1, 3, -4, 2, -1, -3, -2, -1, -5, 2, 2, -1, 127, 1, 1, 3, 1, 127, -4, -1, -3, 127, 2, -1, 1, 127, -3, 127, 1, 127, 3, 1, 1, 1, -1, 1, 2, 1, 4, 127, -1, 127, 127, -2, -1, 1, 0, 1, 1, 127, -1, -2, 127, 1, 1, 1, 2, -2, 1, 127, 127, -1, 2, 1, 127, -6, 0, 127, 2, 3, 11, 1, -2, 127, 1, 5, 127, 127, 127, 1, -1, -1, 127, -2, 1, -1, -1, 2, 2, 7, -2, 3, 2, 1, 127, 0, 127, 127, 127, 6, 1, -1, 1, 2, -1, 1, 127, 1, -1, -3, 127, -1, -2, 0, 1, 1, 5, 2, 0, 127, 
0, -1, 1, 1, 127, 127, 127, 127, 127, 127, 127, 127, 0, 127, 127, -2, -2, -1, -4, 1, 1, 127, 3, 127, 127, 127, -4, 1, 2, 127, 127, 127, -2, 127, 127, 127, -1, 127, 127, 127, 127, -1, -4, 1, 0, 0, -3, 127, 127, -1, 1, 10, 127, 2, 127, -2, -1, 127, 127, 1, 127, 127, 127, 127, 1, -1, -1, 127, 1, 127, 127, -22, 127, 3, 1, 3, 127, 127, -1, 5, 127, 127, 127, 127, -1, 1, -3, 127, -2, 127, 2, 2, 127, 11, -4, 0, 1, -1, 4, -1, -1, 127, -1, 127, -1, 127, 127, 127, 2, -1, -1, 127, 11, 20, 127, 9, -1, -7, -10, -16, 0, 127, 2, 2, 127, -1, 1, -1, 1, 1, 1, -1, 1, 127, -1, 6, 6, -4, 1, 9, 3, 8, 1, -1, -4, 0, 2, 1, 1, -1, -4, 127, 127, 127, 127, -2, 3, 127, 5, 127, 3, 127, 1, 1, 1, 0, 1, -1, 5, 127, -13, 6, 2, 1, 127, 5, -1, 6, -1, 5, 3, -4, 127, 127, -1, 5, 1, 1, 1, 2, 2, 1, 2, 127, -2, -1, 1, 127, -1, -1, 
-3, 2, 2, -1, 127, 2, 1, 2, 127, 127, -1, 2, 9, 4, 127, 127, 0, 1, 1, 5, 127, 2, 2, 127, 127, 1, 2, 1, 127, 1, 1, -2, 2, 5, 1, 127, 1, -1, 1, -3, 127, 1, 127, -1, -5, -3, -1, 127, 1, 2, 127, 127, 1, 1, 1, -5, -5, 1, 2, 1, 3, 1, 1, 3, 1, 2, 127, 1, -1, 4, 1, 1, 3, -2, 1, 4, 2, -5, 127, 2, 1, 5, 127, 3, 2, 3, 127, 1, 1, 5, 1, 1, 0, 1, 1, 3, 127, 1, 1, -1, 1, 4, 1, 1, 2, 2, 0, 8, -1, 4, 2, 1, 2, 2, -1, 8, 1, 3, 3, 1, 127, 127, 3, 127, 1, 127, 1, 3, 127, 5, 1, 0, 9, 1, -1, 2, 3, 127, 1, 3, 3, 1, -1, 1, -1, 2, 2, 127, 3, 1, 2, 4, 7, 1, 2, 4, 1, -2, 127, 1, 2, 2, -1, 0, 1, 2, 1, 1, 127, 2, 1, 1, -3, 5, 12, 4, 1, 4, -28, -2, 4, 1, 1, 127, 1, 1, 127, 6, 127, 2, 1, 24, 127, 3, -1, -1, 127, 1, 1, 5, 
4, 0, -9, 127, 127, -2, 4, -14, -1, 5, 2, 0, 1, 0, 127, 127, -3, 2, 6, 127, 8, 1, 3, -1, 127, -3, 0, 127, 1, 127, 3, -3, 1, 0, -1, -1, -4, -1, -2, 0, -1, 2, 9, 127, -3, -1, 127, 1, 127, 127, 127, 127, 6, -1, 127, 127, 1, 127, 1, 1, 2, 0, 3, 1, 6, -1, -2, -5, 6, 2, 3, -1, -1, -1, 2, 0, 1, 4, 127, -1, 1, -3, -12, 127, 1, 1, 1, 127, 127, 1, -1, 127, 1, 2, 1, 127, 1, 1, 127, 127, 1, 3, 127, 127, 1, -1, 127, 0, 1, -4, 127, -2, 0, 3, 2, 3, 127, 127, 1, 3, -1, 127, 4, 127, 3, 1, -1, 3, 1, 1, 127, -1, 1, -3, -1, 1, 8, 1, 3, -4, 2, 4, 2, 127, 127, 4, 1, 127, 127, -2, 1, 127, 4, 127, -1, 127, 127, 127, 127, 1, -3, 127, 1, 2, -1, 127, 127, -1, 1, -2, 0, -1, 127, 4, -1, 1, -3, 127, 127, -1, 127, -3, 1, 1, 127, -2, -2, 127, 1, -2, 127, 1, 127
                    };
                }
                else if (ii == 0)
                {
                    input = new byte[] { 5, 2, 4, 7, 0, 0, 7, 1, 7 };
                }
                else
                {
                    input = new byte[32];
                    Random rnd = new Random();

                    for (int i = 0; i < input.length; i++)
                    {
                        input[i] = (byte) (65 + rnd.nextInt(64));
                    }
                }

                int size = input.length;
                MTFT mtft = new MTFT();
                byte[] transform = new byte[size];
                byte[] reverse = new byte[size];

                for (int i = 0; i < input.length; i++)
                {
                    transform[i] = input[i];
                }

                System.out.println("\nTest "+(ii+1));
                System.out.print("Input     : ");

                for (int i = 0; i < size; i++)
                {
                    System.out.print((input[i] & 0xFF) + " ");
                }

                transform = mtft.forward(transform, 0);
                System.out.println();
                System.out.print("Transform : ");

                for (int i = 0; i < size; i++)
                {
                    System.out.print((transform[i] & 0xFF) + " ");
                }

                reverse = mtft.inverse(transform, 0);
                System.out.println();
                System.out.print("Reverse   : ");

                for (int i = 0; i < size; i++)
                {
                    System.out.print((reverse[i] & 0xFF) + " ");
                }

                System.out.println();
                boolean ok = true;

                for (int i = 0; i < size; i++)
                {
                    if (reverse[i] != input[i])
                    {
                        ok = false;
                        break;
                    }
                }

                System.out.println((ok == true) ? "Identical" : "Different");

     //           if (ok == false)
       //            System.exit(1);
            }
        }


        // Speed Test
        {
            System.out.println("\nSpeed Test");

            byte[] input = new byte[10000];
            byte[] transform = new byte[input.length];
            MTFT mtft = new MTFT();
            byte[] reverse = new byte[input.length];
            long delta1 = 0, delta2 = 0;
            long before, after;
            int iter = 20000;

            for (int ii = 0; ii < iter; ii++)
            {
                Random rnd = new Random();

                for (int i = 0; i < input.length; i++)
                {
                    input[i] = (byte) (rnd.nextInt(256));
                }

                before = System.nanoTime();
                transform = mtft.forward(input, 0);
                after = System.nanoTime();
                delta1 += (after - before);
                before = System.nanoTime();
                reverse = mtft.inverse(transform, 0);
                after = System.nanoTime();
                delta2 += (after - before);
            }

            System.out.println("Iterations: "+iter);
            System.out.println("MTFT Forward transform [ms]: " + delta1 / 1000000);
            System.out.println("MTFT Reverse transform [ms]: " + delta2 / 1000000);
        }
    }
}
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

package kanzi.util.sampling;


public interface DownSampler
{
    public void subSampleHorizontal(int[] input, int[] output);


    public void subSampleVertical(int[] input, int[] output);


    public void subSample(int[] input, int[] output);

    
    // An implementation can support a fixed scaling factor or a range of
    // scaling factors.
    public boolean supportsScalingFactor(int factor);
}
